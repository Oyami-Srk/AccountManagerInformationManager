package priv.shiroko.amis.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class UpdateObject {
    // non-null value in from will overwrite to
    public static void update(Object to, Object from) {
        if (!to.getClass().isAssignableFrom(from.getClass()))
            return;

        Method[] methods = to.getClass().getMethods();
        for (Method Getter : methods) {
            if (Getter.getDeclaringClass().equals(to.getClass()) && Getter.getName().startsWith("get")) {
                String getterName = Getter.getName();
                String setterName = getterName.replace("get", "set");
                try {
                    Method Setter = to.getClass().getMethod(setterName, Getter.getReturnType());
                    Object value = Getter.invoke(from);
                    if (value != null && !value.equals(Getter.invoke(to))) {
                        Setter.invoke(to, value);
                    }
                } catch (Exception exception) {
                    log.error("Error when update object: " + exception.toString());
                }
            }
        }
    }
}
