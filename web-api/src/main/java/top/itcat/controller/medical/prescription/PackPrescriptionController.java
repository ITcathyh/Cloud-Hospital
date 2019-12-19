package top.itcat.controller.medical.prescription;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.aop.LineConvertHump;
import top.itcat.aop.auth.annotation.RoleCheck;
import top.itcat.bean.diagnose.prescription.pack.PackPrescriptionReq;
import top.itcat.bean.diagnose.prescription.pack.ReturnMedicineReq;
import top.itcat.controller.action.ControllerHelper;
import top.itcat.exception.CommonException;
import top.itcat.rpc.client.MedicalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.medical.DispenseMedicineRequest;
import top.itcat.rpc.service.medical.DrugRepercussionRequest;
import top.itcat.rpc.service.model.PrescriptionGroupCatalogEnum;
import top.itcat.util.GetBaseResponUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 发药退药Controller
 * <p> 主要包括 发药、退药
 */

/**
 * todo test
 * 发药退药Controller
 */
@RestController
@RequestMapping("/diagnose/prescription")
@Slf4j
@RoleCheck
public class PackPrescriptionController {
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private MedicalServiceClient medicalServiceClient;
    @Autowired
    private ControllerHelper controllerHelper;

    /**
     * 发药
     * 请求方法：Post
     *
     * @return 状态
     * @see PackPrescriptionReq
     */
    @PostMapping("/pack")
    public String pack(@LineConvertHump PackPrescriptionReq req,
                       HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        long operatorId = Long.parseLong((String) claims.get("id"));
        DispenseMedicineRequest rpcReq = new DispenseMedicineRequest();
        rpcReq.setMedicalRecordNo(req.getMedicalRecordNo());
        rpcReq.setOperatorId(operatorId);

        try {
            if (medicalServiceClient.dispenseMedicine(rpcReq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (CommonException e) {
            return GetBaseResponUtil.getBaseRspStr(403, "发药失败");
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 退药
     * 请求方法：Post
     *
     * @param req 退药Bean
     * @return 状态
     * @see DrugRepercussionRequest
     */
    @PostMapping("/item/return")
    public String returnMedicine(@LineConvertHump ReturnMedicineReq req,
                                 HttpServletRequest request) {
        DrugRepercussionRequest rpcReq = new DrugRepercussionRequest();
        Claims claims = (Claims) request.getAttribute("claims");
        long operatorId = Long.parseLong((String) claims.get("id"));

        rpcReq.setOperatorId(operatorId);
        rpcReq.setId(req.getItemId());
//        rpcReq.setIds(Collections.singletonList(req.getItemId()));
        rpcReq.setCatalog(PrescriptionGroupCatalogEnum.findByValue(req.getCategory()));

        try {
            if (medicalServiceClient.drugRepercussion(rpcReq) == null) {
                return GetBaseResponUtil.getDefaultErrRspStr();
            }
        } catch (CommonException e) {
            return GetBaseResponUtil.getBaseRspStr(403, "退药失败");
        }

        return GetBaseResponUtil.getSuccessRspStr();
    }
}
