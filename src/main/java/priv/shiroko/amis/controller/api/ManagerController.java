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
        } catch (Exception e) {
            e.printStackTrace();
            failedRows.add(analysisContext.readRowHolder().getRowIndex());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    public void onException(Exception exception, AnalysisContext context) throws Exception {
        exception.printStackTrace();
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
        return "????????????";
    }

    @GetMapping("/export")
    public ModelAndView exportManager(Manager query) {
        ModelAndView mav = new ModelAndView(new EasyExcelView("??????????????????-"
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
                    .sheet("???????????????").doRead();
            log.info("????????????, ??????" + String.valueOf(failedRows.size()) + "??????????????????" + String.valueOf(succeedRows.size()) + "??????????????????");
            result.setStatus(ApiResult.Status.OK);
            HashMap<String, List<Integer>> resultData = new HashMap<>();
            resultData.put("failed", failedRows);
            resultData.put("succeed", succeedRows);
            result.setData(resultData);
            result.setMessage("????????????, ??????" + String.valueOf(failedRows.size()) + "??????????????????" + String.valueOf(succeedRows.size()) + "??????????????????");
        } catch (Exception exception) {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("??????Excel?????????");
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
        ModelAndView mav = new ModelAndView(new EasyExcelView("????????????"));
        mav.addObject("class", Manager.class);
        List<Manager> empty = new ArrayList<>();
        mav.addObject("data", empty);
        return mav;
    }
}
