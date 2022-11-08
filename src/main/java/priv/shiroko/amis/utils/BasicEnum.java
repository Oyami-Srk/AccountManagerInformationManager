package priv.shiroko.amis.utils;

public interface BasicEnum {
    String getName();

    static <T extends BasicEnum> T fromName(String name, Class<T> type) throws Exception {
        if (name == null || name.isEmpty()) return null;
        for (T e : type.getEnumConstants()) {
            if (e.getName().equals(name))
                return e;
        }
        throw new Exception("Enum[" + type.getTypeName() + "] has no enum with name " + name);
    }

}
