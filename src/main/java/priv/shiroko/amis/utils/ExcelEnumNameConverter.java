package priv.shiroko.amis.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.lang.reflect.Method;

public class ExcelEnumNameConverter implements Converter<Enum<?>> {
    @Override
    public WriteCellData<?> convertToExcelData(Enum<?> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Method getName = value.getClass().getMethod("getName");
        return new WriteCellData<String>((String) getName.invoke(value));
    }
}
