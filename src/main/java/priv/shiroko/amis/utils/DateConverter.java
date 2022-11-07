package priv.shiroko.amis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class DateConverter implements Converter<String, Date> {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String from) {
        try {
            return simpleDateFormat.parse(from);
        } catch (ParseException e) {
            log.warn("Error when parse '" + from + "' to date: " + e.toString());
            return null;
        }
    }
}
