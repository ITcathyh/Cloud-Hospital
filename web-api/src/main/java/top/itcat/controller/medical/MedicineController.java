package top.itcat.controller.medical;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.CommonDelReq;
import top.itcat.bean.charge.AddMedicinesReq;
import top.itcat.bean.charge.QueryMedicinesRequest;
import top.itcat.bean.charge.UpdateMedicineReq;
import top.itcat.entity.medical.Medicine;
import top.itcat.rpc.client.MedicalServiceClient;
import top.itcat.rpc.service.medical.AddOrUpdateMedicineRequest;
import top.itcat.rpc.service.medical.GetMedicineRequest;
import top.itcat.rpc.service.medical.GetMedicineResponse;
import top.itcat.rpc.service.model.MedicineCategoryEnum;
import top.itcat.util.GetBaseResponUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 药品Controller
 * <p> 主要包括 药品的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/medical/medicine")
@Slf4j
@RoleCheck
public class MedicineController {
    @Autowired
    private MedicalServiceClient medicalServerClient;

    /**
     * 增加药品
     * 请求方法：Post
     *
     * @return 状态
     * @see AddMedicinesReq
     */
    @PostMapping("/manage/add")
    public String addMedicine(@LineConvertHump AddMedicinesReq req) {
        AddOrUpdateMedicineRequest rpcReq = new AddOrUpdateMedicineRequest();
        rpcReq.setBean(req.getList().
                parallelStream().map(Medicine::convertRPCBean).
                collect(Collectors.toList()));
        if (medicalServerClient.addOrUpdateMedicine(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新药品
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateMedicineReq
     */
    @PostMapping("/manage/update")
    public String updateMedicine(@LineConvertHump UpdateMedicineReq req) {
        AddOrUpdateMedicineRequest rpcReq = new AddOrUpdateMedicineRequest();
        rpcReq.setBean(Collections.
                singletonList(Medicine.convertRPCBean(req.getMedicine())));
        if (medicalServerClient.addOrUpdateMedicine(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除药品
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delMedicine(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.Medicine rpcbean = new top.itcat.rpc.service.model.Medicine();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateMedicineRequest rpcReq = new AddOrUpdateMedicineRequest();
        rpcReq.setBean(Collections.singletonList(rpcbean));
        if (medicalServerClient.addOrUpdateMedicine(rpcReq) == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        }
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询药品
     * 请求方法：Any
     *
     * @return 获取到的药品信息
     * @see GetMedicineRequest
     * @see Medicine
     */
    @RequestMapping("/get")
    @RoleCheck()
    public String getMedicine(QueryMedicinesRequest req) {
        GetMedicineRequest rpcReq = new GetMedicineRequest();

        if (req.getIds() != null) {
            rpcReq.setIds(req.getIds());
        }
        if (req.getLimit() != null) {
            rpcReq.setLimit(req.getLimit());
        }

        if (req.getOffset() != null) {
            rpcReq.setOffset(req.getOffset());
        }
        if (req.getCategory() != null) {
            rpcReq.setCategory(MedicineCategoryEnum.findByValue(req.getCategory()));
        }

        rpcReq.setSearchKey(req.getSearchKey());
        GetMedicineResponse rsp = medicalServerClient.getMedicine(rpcReq);

        if (rsp == null) {
            return GetBaseResponUtil.getDefaultErrRspStr();
        } else if (rsp.getBeanListSize() == 0) {
            rsp.setBeanList(new ArrayList<>());
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = rsp.getBeanList().parallelStream()
                .map(Medicine::convert)
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }
}
