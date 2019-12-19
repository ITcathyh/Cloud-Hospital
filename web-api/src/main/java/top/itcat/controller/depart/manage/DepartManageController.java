package top.itcat.controller.depart.manage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.charge.AddDepartmentsReq;
import top.itcat.bean.charge.UpdateDepartmentReq;
import top.itcat.bean.hospital.GetDepartmentReq;
import top.itcat.entity.hospital.Department;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.service.hospital.GetDepartmentRequest;
import top.itcat.rpc.service.hospital.GetDepartmentResponse;
import top.itcat.rpc.service.model.DepartClassificationEnum;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 科室组套Controller
 * <p> 主要包括 科室的增删改查
 */

/**
 * Test Done
 */
@RestController
@RequestMapping("/department")
public class DepartManageController {
    @Autowired
    private HospitalServiceClient hospitalServiceClient;

    /**
     * 增加科室
     * 请求方法：Post
     *
     * @return 状态
     * @see AddDepartmentsReq
     */
    @PostMapping("/manage/add")
    public String addDepartment(@LineConvertHump AddDepartmentsReq req) {
        hospitalServiceClient.addOrUpdateDepartment(req.getList().
                parallelStream().map(Department::convertRPCBean).
                collect(Collectors.toList()));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新科室
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateDepartmentReq
     */
    @RequestMapping(value = "/manage/update", produces = {"application/json;charset=UTF-8"})
    public String updateDepartment(@LineConvertHump UpdateDepartmentReq req) {
        hospitalServiceClient.addOrUpdateDepartment(Collections.
                singletonList(Department.convertRPCBean(req.getDepartment())));

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除科室
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delDepart(@RequestBody CommonDelReq req) {
        top.itcat.rpc.service.model.Department rpcbean = new top.itcat.rpc.service.model.Department();
        rpcbean.setId(req.getId());
        rpcbean.setValid(-1);

        hospitalServiceClient.addOrUpdateDepartment(Collections.singletonList(rpcbean));
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询科室
     * 请求方法：Any
     *
     * @return 获取到的科室信息
     * @see GetDepartmentRequest
     * @see Department
     */
    @RequestMapping("/get")
    @RoleCheck(needLogin = false)
    public String getDepart(GetDepartmentReq req) {
        GetDepartmentRequest rpcReq = new GetDepartmentRequest();

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }

        if (req.getSearchKey() != null) {
            rpcReq.setSearchKey(req.getSearchKey());
        }

        if (req.getType() != null) {
            rpcReq.setClassification(DepartClassificationEnum.findByValue(req.getType()));
        }

        GetDepartmentResponse rsp = hospitalServiceClient.getDepartment(rpcReq);
        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rsp.getBeanList()
                .parallelStream()
                .map(Department::convert)
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("total", rsp.getTotal());
        json.put("count", rsp.getBeanListSize());
        return json.toJSONString();
    }
}
