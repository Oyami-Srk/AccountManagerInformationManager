package priv.shiroko.amis.view;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class EasyExcelView extends AbstractView {
    private final String fileName;

    public EasyExcelView(String fileName) {
        this.setContentType("application/vnd.ms-excel");
        this.fileName = fileName;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = this.fileName + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        ExcelWriter writer = EasyExcel.write(response.getOutputStream())
                .excelType(ExcelTypeEnum.XLSX)
                .autoCloseStream(true)
                .head((Class<?>) model.get("class"))
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("客户经理表").build();
        writer.write((List<?>) model.get("data"), writeSheet);
        writer.writeContext();
        writer.finish();
        writer.close();
    }
}
