package priv.shiroko.amis.controller.api;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import priv.shiroko.amis.entity.Manager;
import priv.shiroko.amis.mapper.LearnMapper;
import priv.shiroko.amis.mapper.ManagerMapper;
import priv.shiroko.amis.utils.ApiResult;
import priv.shiroko.amis.view.EasyExcelView;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
class ManagerExcelListener implements ReadListener<Manager> {

    // TODO: Batch insert
    private final ManagerMapper mapper;
    private final List<Integer> failedRows;
    private final List<Integer> succeedRows;

    public ManagerExcelListener(ManagerMapper mapper, List<Integer> failedRows, List<Integer> succeedRows) {
        this.mapper = mapper;
        this.failedRows = failedRows;
        this.succeedRows = succeedRows;
    }

    @Override
    public void invoke(Manager manager, AnalysisContext analysisContext) {
        try {
            mapper.add(manager);
            succeedRows.add(analysisContext.readRowHolder().getRowIndex());
        } catch (Exception ignore) {
            failedRows.add(analysisContext.readRowHolder().getRowIndex());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    public void onException(Exception exception, AnalysisContext context) throws Exception {
        failedRows.add(context.readRowHolder().getRowIndex());
    }
}

@RestController
@RequestMapping("/api/manager")
@Slf4j
public class ManagerController extends BasicController<Manager, ManagerMapper> {
    @Resource
    ManagerMapper managerMapper;

    @Resource
    LearnMapper learnMapper;

    @Override
    protected ManagerMapper getMapper() {
        return managerMapper;
    }

    @Override
    protected String getEntityName() {
        return "客户经理";
    }

    @GetMapping("/export")
    public ModelAndView exportManager(Manager query) {
        ModelAndView mav = new ModelAndView(new EasyExcelView("客户经理数据-"
                + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())));
        mav.addObject("class", Manager.class);
        mav.addObject("data", managerMapper.get(query, null, null));
        return mav;
    }

    @PostMapping("/import")
    public ApiResult importManager(MultipartFile file) {
        ApiResult result = new ApiResult();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            List<Integer> failedRows = new ArrayList<>();
            List<Integer> succeedRows = new ArrayList<>();
            EasyExcel.read(inputStream, Manager.class, new ManagerExcelListener(managerMapper, failedRows, succeedRows))
                    .sheet("客户经理表").doRead();
            log.info("导入结束, 共计" + String.valueOf(failedRows.size()) + "个失败条目，" + String.valueOf(succeedRows.size()) + "个成功条目。");
            result.setStatus(ApiResult.Status.OK);
            HashMap<String, List<Integer>> resultData = new HashMap<>();
            resultData.put("failed", failedRows);
            resultData.put("succeed", succeedRows);
            result.setData(resultData);
            result.setMessage("导入结束, 共计" + String.valueOf(failedRows.size()) + "个失败条目，" + String.valueOf(succeedRows.size()) + "个成功条目。");
        } catch (Exception exception) {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("导入Excel失败。");
            log.trace("Error import excel.", exception);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException exception) {
                    log.trace("Error close stream.", exception);
                }
            }
        }
        return result;
    }

    @GetMapping("/template")
    public ModelAndView getTemplate() {
        ModelAndView mav = new ModelAndView(new EasyExcelView("导入模版"));
        mav.addObject("class", Manager.class);
        List<Manager> empty = new ArrayList<>();
        mav.addObject("data", empty);
        return mav;
    }
}
