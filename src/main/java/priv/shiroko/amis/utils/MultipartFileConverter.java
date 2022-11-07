package priv.shiroko.amis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Slf4j
public class MultipartFileConverter implements Converter<MultipartFile, byte[]> {
    @Override
    public byte[] convert(MultipartFile source) {
        try {
            return source.getBytes();
        } catch (IOException e) {
            log.error("IO Exception when converting MultipartFile to byte[]: " + e.toString());
            return new byte[0];
        }
    }
}
