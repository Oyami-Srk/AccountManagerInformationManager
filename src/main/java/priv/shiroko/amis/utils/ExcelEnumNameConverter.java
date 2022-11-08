package priv.shiroko.amis.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.lang.reflect.Method;

public class ExcelEnumNameConverter implements Converter<Enum<? extends BasicEnum>> {
    @Override
    public WriteCellData<?> convertToExcelData(Enum<? extends BasicEnum> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Method getName = value.getClass().getMethod("getName");
        return new WriteCellData<String>((String) getName.invoke(value));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Enum<? extends BasicEnum> convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String name = cellData.getStringValue();
        Class<? extends BasicEnum> type = (Class<? extends BasicEnum>) contentProperty.getField().getType();
        return (Enum<? extends BasicEnum>) BasicEnum.fromName(name, type);
    }
}
