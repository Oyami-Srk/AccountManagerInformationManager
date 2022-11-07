package priv.shiroko.amis.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Method;

public class JsonEnumNameSerializer extends StdSerializer<Enum<?>> {
    public JsonEnumNameSerializer(Class<Enum<?>> t) {
        super(t);
    }

    public JsonEnumNameSerializer() {
        super((Class<Enum<?>>) null);
    }

    @Override
    public void serialize(Enum<?> value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String name = null;
        try {
            Method getName = value.getClass().getMethod("getName");
            name = (String) getName.invoke(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        jsonGenerator.writeString(name);
    }
}
