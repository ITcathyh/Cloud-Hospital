package top.itcat.controller.registration.site;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.googlecode.protobuf.format.JsonFormat;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.aop.limiter.annotation.RateLimit;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.charge.SubmitSiteRegistrationReq;
import top.itcat.bean.registration.GetRegistrationReq;
import top.itcat.cache.manage.DefaultCacheManager;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.controller.action.ControllerHelper;
import top.itcat.controller.action.RegistrationHelper;
import top.itcat.entity.Patient;
import top.itcat.exception.EmptyResultException;
import top.itcat.exception.InternalException;
import top.itcat.exception.InvalidParamException;
import top.itcat.mq.MQProducer;
import top.itcat.pb_gen.api.registration.Registration;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.diagnose.AddOrUpdateRegistrationRequest;
import top.itcat.rpc.service.diagnose.CancelRegistrationRequest;
import top.itcat.rpc.service.diagnose.GetRegistrationRequest;
import top.itcat.rpc.service.diagnose.GetRegistrationResponse;
import top.itcat.rpc.service.model.BasePatientInfo;
import top.itcat.rpc.service.model.GenderEnum;
import top.itcat.rpc.service.model.RegistrationStatusEnum;
import top.itcat.rpc.service.user.AddOrUpdatePatientRequest;
import top.itcat.util.GetBaseResponUtil;
import top.itcat.util.IDGeneratorUtil;
import top.itcat.util.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static top.itcat.constant.RedisKey.*;

/**
 * 挂号Controller
 * <p> 主要包括 挂号、退号、就诊、诊毕、查看挂号、查看所有挂号
 */
@RestController
@RequestMapping("/registration")
@RoleCheck
@Slf4j
public class SiteRegistrationController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private DefaultCacheManager cache;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private RegistrationHelper registrationHelper;


    /**
     * 挂号
     * 使用Redis和Kafka消息队列快速预响应复杂逻辑
     * 并使用令牌通算法进行接口限流
     * 请求方法：Post
     *
     * @return 挂号信息
     * @see SubmitSiteRegistrationReq
     */
    @PostMapping("/submit")
    @RateLimit(limitNum = 1000)
    @RoleCheck(needLogin = false)
    public String submit(@LineConvertHump SubmitSiteRegistrationReq req,
                         HttpServletRequest request) {
        if (req.getSchedulePlanId() <= 0) {
            return GetBaseResponUtil.getBaseRspStr(400, "请求有误");
        }

        String key = String.format(SUBMIT_SCHEDULE_SITE_KEY, req.getSchedulePlanId());
        long operatorId = 0;

        if (request.getAttribute("claims") != null) {
            operatorId = Long.valueOf((String) ((Claims) request.getAttribute("claims")).get("id"));
        }

        // 从缓存读取标记，若不为空，则无余号，进行快速响应
        if (cache.get("over:" + key) != null) {
            return GetBaseResponUtil.getBaseRspStr(403, "挂号数量不足");
        }

        BasePatientInfo patientInfo = new BasePatientInfo();
        patientInfo.setAge(req.getAge());
        patientInfo.setBirth(req.getBirthday());
        patientInfo.setAddress(req.getAddress());
        patientInfo.setGender(GenderEnum.findByValue(req.getGender()));
        patientInfo.setIdNum(req.getIdNum());
        patientInfo.setName(req.getName());
        patientInfo.setPhone(req.getPhone());

        // 根据排班计划ID和患者身份证设置分布式锁，若设置失败，则代表重复挂号
        if (!RedisUtil.setnx(redisTemplate, String.format(IS_DUPL_SUBMIT_SCHEDULE_SITE_KEY,
                req.getSchedulePlanId(), req.getIdNum()), 1, 15 * 60)) {
            return GetBaseResponUtil.getBaseRspStr(403, "请勿重复预约");
        }

        // 预减号数，避免高并发导致响应缓慢且DB崩溃
        int result = preSubmit(key, req.getSchedulePlanId());

        if (result == -1) {
            return GetBaseResponUtil.getBaseRspStr(403, "挂号数量不足");
        }

        // 发送Kafka消息增加或更新患者信息
//        addPatient(Patient.convertPatient(patient));

        CommonThreadPoolFactory.getDefaultThreadPool().execute(() -> {
            top.itcat.rpc.service.model.Patient patient = new top.itcat.rpc.service.model.Patient();
            patient.setUserInfo(patientInfo);
            AddOrUpdatePatientRequest addOrUpdatePatientRequest = new AddOrUpdatePatientRequest();
            addOrUpdatePatientRequest.setUsers(Collections.singletonList(patient));

            if (userServiceClient.addOrUpdatePatient(addOrUpdatePatientRequest) == null) {
            }
        });

        // 根据日期和序号生成唯一病历号
        Long medicalRecordNo = IDGeneratorUtil.genPrimaryID(null, redisTemplate, "medical_record_no");
//        DateTime dateTime = new DateTime();
//        DateTime nextDay = dateTime.plusDays(1).withMillisOfDay(0);
//        long expireTime = (nextDay.getMillis() - dateTime.getMillis()) / 1000;
//        long curNum = (long) ((Jedis) redisTemplate
//                .getConnectionFactory()
//                .getConnection()
//                .getNativeConnection())
//                .eval(GET_INCRING_SEQ, 2, String.format("regi_seq:%d", req.getSchedulePlanId()), String.valueOf(expireTime));

        if (medicalRecordNo == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        AddOrUpdateRegistrationRequest registrationRequest = new AddOrUpdateRegistrationRequest();
        top.itcat.rpc.service.model.Registration registration = new top.itcat.rpc.service.model.Registration();

        registration.setBillingCategoryId(req.getBillingCategoryId());
        registration.setIdentityCardNo(req.getIdNum());
        registration.setMedicalRecordNo(medicalRecordNo);
        registration.setRegistrationTime(System.currentTimeMillis());
        registration.setSchedulePlanId(req.getSchedulePlanId());

        registrationRequest.setBean(registration);
        registrationRequest.setNeedBook(req.getNeedBook() == null ? false : req.getNeedBook());
        registrationRequest.setOperatorId(operatorId);

        try {
            // 发送Kafka消息增加挂号记录
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(registrationRequest);
            mqProducer.send("add_registration", jsonObject.toJSONString());
        } catch (Exception e) {
            try {
                if (redisTemplate.opsForValue().increment(key, 1) == null) {
                    log.error("incr err,key({})", key);
                }
            } catch (Exception ee) {
                log.error("incr err,key({}),err:", key, ee);
            }
            log.error("SubmitSiteRegistration err,", e);
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        Registration.SubmitSiteRegistrationResponse.Builder builder = Registration.SubmitSiteRegistrationResponse.newBuilder();
        builder.setBase(GetBaseResponUtil.getSuccessRsp())
                .setMedicalRecordNo(medicalRecordNo);
        return JsonFormat.printToString(builder.build());
    }

    /**
     * 退号
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CancelRegistrationRequest
     */
    @PostMapping("/cancel")
    public String cancelRegistration(@RequestBody CommonDelReq req) {
        CancelRegistrationRequest rpcreq = new CancelRegistrationRequest();
        rpcreq.setId(req.getId());

        GetRegistrationRequest getRegistrationRequest = new GetRegistrationRequest();
        getRegistrationRequest.setIds(Collections.singletonList(req.getId()));
        GetRegistrationResponse getRegistrationResponse = diagnoseServiceClient.getRegistration(getRegistrationRequest);

        if (getRegistrationResponse == null) {
            throw new InternalException();
        } else if (getRegistrationResponse.getBeanListSize() == 0) {
            return GetBaseResponUtil.getBaseRspStr(400, "未找到对应挂号记录");
        } else if (getRegistrationResponse.getBeanList().get(0).getStatus() != RegistrationStatusEnum.UnSeen) {
            return GetBaseResponUtil.getBaseRspStr(403, "挂号已就诊");
        }

        try {
            if (diagnoseServiceClient.cancelRegistration(rpcreq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (InvalidParamException e) {
            return GetBaseResponUtil.getBaseRspStr(403, "请求参数有误");
        } catch (EmptyResultException e) {
            return GetBaseResponUtil.getBaseRspStr(403, "账单已日结");
        }

        top.itcat.rpc.service.model.Registration registration = getRegistrationResponse.getBeanList().get(0);
        redisTemplate.delete(String.format(IS_DUPL_SUBMIT_SCHEDULE_SITE_KEY,
                registration.getSchedulePlanId(), registration.getIdentityCardNo()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 诊毕
     * 请求方法：Post
     *
     * @param registration 诊毕的挂号对象
     * @return 状态
     * @see AddOrUpdateRegistrationRequest
     */
    @PostMapping("/check/over")
    public String checkRegistrationOver(@RequestBody top.itcat.entity.registration.Registration registration) {
        registration.setStatus(RegistrationStatusEnum.Done.getValue());
        AddOrUpdateRegistrationRequest rpcReq = new AddOrUpdateRegistrationRequest();
        rpcReq.setBean(top.itcat.entity.registration.Registration.convertRPCBean(registration));

        if (diagnoseServiceClient.addOrUpdateRegistration(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 确认病人开始就诊
     * 更新挂号状态
     *
     * @param req 挂号ID
     * @return 状态
     * @see AddOrUpdateRegistrationRequest
     */
    @PostMapping("/check")
    public String checkRegistration(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.Registration registration = new top.itcat.rpc.service.model.Registration();
        registration.setId(req.getId());
        registration.setStatus(RegistrationStatusEnum.Done);
        AddOrUpdateRegistrationRequest rpcReq = new AddOrUpdateRegistrationRequest();
        rpcReq.setBean(registration);

        diagnoseServiceClient.addOrUpdateRegistration(rpcReq);
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询挂号单
     * 请求方法：Any
     *
     * @return 获取到的挂号信息
     * @see GetRegistrationRequest
     * @see Registration
     */
    @RequestMapping("/get")
    @RoleCheck(needLogin = false)
    public String getRegistration(GetRegistrationReq req) {
        GetRegistrationRequest rpcReq = new GetRegistrationRequest();

        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setIds(req.getIds());
        }

        rpcReq.setSearchKey(req.getSearchKey());

        if (req.getOffset() != null) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.getLimit() != null) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getDepartId() != null) {
            rpcReq.setDepartId(req.getDepartId());
        }

        if (req.getDoctorId() != null) {
            rpcReq.setDoctorId(req.getDoctorId());
        }

        GetRegistrationResponse rsp = diagnoseServiceClient.getRegistration(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        List<top.itcat.entity.registration.Registration> registrations = registrationHelper.packRegistrations(rsp.getBeanList());

        if (registrations == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = registrations.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 查询所有挂号单
     * 请求方法：Any
     *
     * @return 获取到的所有挂号信息
     * @see GetRegistrationRequest
     * @see Registration
     */
    @RequestMapping("/getall")
    public String getAllRegistration(GetRegistrationReq req,
                                     HttpServletRequest request) {
        GetRegistrationRequest rpcReq = new GetRegistrationRequest();

        if (req.getOffset() != null) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.getLimit() != null) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getSearchKey() != null) {
            rpcReq.setSearchKey(req.getSearchKey());
        }

        if (req.getStatus() != null) {
            rpcReq.setStatus(RegistrationStatusEnum.findByValue(req.getStatus()));
        }

        Claims claims = ((Claims) request.getAttribute("claims"));

        if (req.getType() != null) {
            if (req.getType() == 0) {
                rpcReq.setDoctorId(Long.valueOf((String) claims.get("id")));
            } else if (req.getType() == 1) {
                rpcReq.setDepartId(Long.valueOf((String) claims.get("departmentId")));
            } else {
                return GetBaseResponUtil.getBaseRspStr(400, "请求参数有误");
            }
        }

        GetRegistrationResponse rsp = diagnoseServiceClient.getRegistration(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        List<top.itcat.entity.registration.Registration> registrations = registrationHelper.packRegistrations(rsp.getBeanList());

        if (registrations == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = registrations.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    @RateLimit(fieldKeys = {"#id"})
    public int preSubmit(String appokey, long id) {
        long remain = (long) ((Jedis) redisTemplate.getConnectionFactory().
                getConnection().getNativeConnection()).eval(DECR_SCHEDULE_REMAIN_LUA, 1, appokey);

        if (remain <= 0) {
            cache.set("over:" + appokey, true, 0, 10);

            if (remain < 0) {
                return -1;
            }
        }

        return 0;
    }

    private void addPatient(Patient patient) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(patient);
        mqProducer.send("add_up_patient", jsonObject.toJSONString());
    }
}
