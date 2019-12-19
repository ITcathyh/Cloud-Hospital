package top.itcat.controller.diagnose.diagnostic;

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
import top.itcat.bean.diagnose.diagnostic.directory.AddFirstDiagDirsReq;
import top.itcat.bean.diagnose.diagnostic.directory.GetFirstDiagDirReq;
import top.itcat.bean.diagnose.diagnostic.directory.UpdateFirstDiagDirReq;
import top.itcat.entity.diagnose.FirstDiagDir;
import top.itcat.entity.diagnose.SecondDiagDir;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.service.diagnose.*;
import top.itcat.util.GetBaseResponUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 一级诊断目录Controller
 * <p> 主要包括 一级诊断目录的增删改查
 */

/**
 * todo test
 */
@RestController
@RequestMapping("/diagnostic/directory/first")
@Slf4j
public class FirstDiagDirController {
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;

    /**
     * 增加一级诊断目录
     * 请求方法：Post
     *
     * @return 状态
     * @see AddFirstDiagDirsReq
     */
    @PostMapping("/manage/add")
    public String addFirstDiagDir(@LineConvertHump AddFirstDiagDirsReq req) {
        AddOrUpdateFirstDiagDirRequest rpcReq = new AddOrUpdateFirstDiagDirRequest();
        rpcReq.setBeanList(req.getList().
                parallelStream().map(FirstDiagDir::convertRPCBean).
                collect(Collectors.toList()));
        diagnoseServiceClient.addOrUpdateFirstDiagDir(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 更新一级诊断目录
     * 请求方法：Post
     *
     * @param req 更新Bean
     * @return 状态
     * @see UpdateFirstDiagDirReq
     */
    @PostMapping("/manage/update")
    public String updateFirstDiagDir(@LineConvertHump UpdateFirstDiagDirReq req) {
        AddOrUpdateFirstDiagDirRequest rpcReq = new AddOrUpdateFirstDiagDirRequest();
        rpcReq.setBeanList(Collections.
                singletonList(FirstDiagDir.convertRPCBean(req.getDiagnosticDirectory())));
        diagnoseServiceClient.addOrUpdateFirstDiagDir(rpcReq);

        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 删除一级诊断目录
     * 请求方法：Post
     *
     * @param req 删除Bean
     * @return 状态
     * @see CommonDelReq
     */
    @PostMapping("/manage/del")
    public String delFirstDiagDir(@LineConvertHump CommonDelReq req) {
        top.itcat.rpc.service.model.FirstDiagDir rpcbean = new top.itcat.rpc.service.model.FirstDiagDir();
        rpcbean.setValid(-1);
        rpcbean.setId(req.getId());

        AddOrUpdateFirstDiagDirRequest rpcReq = new AddOrUpdateFirstDiagDirRequest();
        rpcReq.setBeanList(Collections.singletonList(rpcbean));
        diagnoseServiceClient.addOrUpdateFirstDiagDir(rpcReq);
        return GetBaseResponUtil.getSuccessRspStr();
    }

    /**
     * 查询一级诊断目录
     * 请求方法：Any
     *
     * @return 获取到的一级诊断目录信息
     * @see GetFirstDiagDirRequest
     * @see FirstDiagDir
     */
    @RequestMapping("/get")
    @RoleCheck
    public String getFirstDiagDir(GetFirstDiagDirReq req) {
        GetFirstDiagDirRequest rpcReq = new GetFirstDiagDirRequest();

        if (req.getIds() != null && !req.getIds().isEmpty()) {
            rpcReq.setIds(req.getIds());
        }

        if (req.isSetOffset()) {
            rpcReq.setOffset(req.getOffset());
        }

        if (req.isSetLimit()) {
            rpcReq.setLimit(req.getLimit());
        }

        rpcReq.setSearchKey(req.getSearchKey());

        GetFirstDiagDirResponse rsp = diagnoseServiceClient.getFirstDiagDir(rpcReq);
        List<FirstDiagDir> firstDiagDirs = rsp.getBeanList()
                .parallelStream()
                .map(FirstDiagDir::convert)
                .sorted(new Comparator<FirstDiagDir>() {
                    @Override
                    public int compare(FirstDiagDir o1, FirstDiagDir o2) {
                        return (int) (o1.getId() - o2.getId());
                    }
                })
                .collect(Collectors.toList());

        if (req.getWithSecond() != null && req.getWithSecond()) {
            GetSecondDiagDirRequest getSecondDiagDirRequest = new GetSecondDiagDirRequest();

            for (FirstDiagDir firstDiagDir : firstDiagDirs) {
                getSecondDiagDirRequest.setFirstDiagDirId(firstDiagDir.getId());
                GetSecondDiagDirResponse getSecondDiagDirResponse = diagnoseServiceClient
                        .getSecondDiagDir(getSecondDiagDirRequest);

                if (getSecondDiagDirResponse == null) {
                    return GetBaseResponUtil.getBaseRspStr(500, "内部服务错误");
                }

                firstDiagDir.setSecondDirecs(getSecondDiagDirResponse
                        .getBeanList()
                        .parallelStream()
                        .map(SecondDiagDir::convert)
                        .collect(Collectors.toList()));
            }
        }

        JSONObject json = GetBaseResponUtil.getSuccessJson();
        JSONArray jsonArray = firstDiagDirs.parallelStream()
                .collect(Collectors.toCollection(JSONArray::new));

        json.put("list", jsonArray);
        json.put("count", rsp.getBeanListSize());
        json.put("total", rsp.getTotal());
        return json.toJSONString();
    }
}