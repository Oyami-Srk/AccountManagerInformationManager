package priv.shiroko.amis.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.HashMap;
import java.util.Map;

public class EntityFilter {
    public static Map<String, Object> filter(Object o, String[] removedAttributes) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(o, TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class));
        for (String attr : removedAttributes) {
            map.remove(attr);
        }
        return map;
    }

    public static Map<String, Object> filter(Object o, String removedAttribute) {
        return filter(o, new String[]{removedAttribute});
    }
}
