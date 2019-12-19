package top.itcat.rpc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.joda.time.DateTime;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.rpc.service.model.SuitableRangeEnum;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class WrapperGenerator {
    public static Wrapper getApplyGroupWrapper(GetApplyGroupRequest req) {
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id desc");

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetDepartmentId()) {
            wrapper = wrapper.eq("department_id", req.getDepartmentId());
        }

        if (req.isSetCreaterId()) {
            wrapper = wrapper.eq("creator_id", req.getCreaterId());
        }

        if (req.isSetRange()) {
            wrapper = wrapper.eq("range", req.getRange());
        }

        if (req.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("name", req.getSearchKey())
                    .or()
                    .like("code", req.getSearchKey());
        }

        if (req.isSetCategory()) {
            wrapper = wrapper.eq("category", req.getCategory().getValue());
        }

        return wrapper;
    }

    public static Wrapper getScheduleRuleWrapper(GetScheduleRuleRequest req) {
        Wrapper wrapper = new EntityWrapper().orderBy("id desc");

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetDepartId()) {
            wrapper = wrapper.eq("department_id", req.getDepartId());
        }

        if (req.isSetDoctorId()) {
            wrapper = wrapper.eq("doctor_id", req.getDoctorId());
        }

        if (req.isSetOnlyNormal()) {
            wrapper = wrapper.eq("valid", 1);
        } else if (!req.isSetNeedExpired() ||
                !req.isNeedExpired()) {
            wrapper = wrapper.ge("valid", 1);
        }

        return wrapper;
    }

    public static void main(String[] args) {
        System.out.println(new DateTime(1562169600000L).toString());
    }

    public static Wrapper getMedicalRecordTemplateWrapper(GetMedicalRecordTemplateRequest getMedicalRecordTemplateRequest) {
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id desc");

        if (getMedicalRecordTemplateRequest.isSetIds()) {
            wrapper = wrapper.in("id", getMedicalRecordTemplateRequest.getIds());
        }

        if (getMedicalRecordTemplateRequest.isSetSearchKey()) {
            wrapper = wrapper.andNew()
                    .like("name", getMedicalRecordTemplateRequest.getSearchKey())
                    .or()
                    .like("code", getMedicalRecordTemplateRequest.getSearchKey());
        }

        if (getMedicalRecordTemplateRequest.isSetRange()) {
            List<Integer> ranges = getMedicalRecordTemplateRequest.getRange().parallelStream().map(SuitableRangeEnum::getValue).collect(Collectors.toList());
            wrapper = wrapper.in("suitable_range", ranges);
        }

        return wrapper;
    }

    public static Wrapper getCommonlyUsedDiagnosticWrapper(GetCommonlyUsedDiagnosticRequest req) {
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id desc");

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetRange()) {
            List<Integer> ranges = req.getRange().parallelStream().map(SuitableRangeEnum::getValue).collect(Collectors.toList());
            wrapper = wrapper.in("suitable_range", ranges);
        }

        if (req.isSetDepartmentId()) {
            wrapper = wrapper.eq("department_id", req.getDepartmentId());
        }

        if (req.isSetDoctorId()) {
            wrapper = wrapper.eq("doctor_id", req.getDoctorId());
        }

        return wrapper;
    }

    public static Wrapper getApplyWrapper(GetApplyRequest req) {
        Wrapper wrapper = new EntityWrapper().eq("valid", 1).orderBy("id desc");

        if (req.isSetIds()) {
            wrapper = wrapper.in("id", req.getIds());
        }

        if (req.isSetMedicalRecordNo()) {
            wrapper = wrapper.eq("medical_record_no", req.getMedicalRecordNo());
        }

        if (req.isSetDoctorId()) {
            wrapper = wrapper.eq("doctor_id", req.getDoctorId());
        }

        if (req.isSetCategory()) {
            wrapper = wrapper.eq("category", req.getCategory());
        }

        return wrapper;
    }

    public static Wrapper getRegistrationWrapper(GetRegistrationRequest req) {
        Wrapper wrapper = new EntityWrapper();

        if (req.isSetIds()) {
            return wrapper.in("id", req.getIds());
        }

        if (req.isSetCurTime()) {
            wrapper = wrapper.eq("registration_time", req.getCurTime());
        }

        if (req.isSetStatus()) {
            wrapper = wrapper.eq("status", req.getStatus().getValue());
        }

        wrapper = wrapper.orderBy("id desc");
        return wrapper;
    }
}
