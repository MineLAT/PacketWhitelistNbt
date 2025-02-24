package xuan.cat.packetwhitelistnbt.api.nbt;

import java.util.Set;

public interface CompoundTag extends Tag {
    TagType getType(String key);

    Object get(String key);

    byte getByte(String key);

    CompoundTag getCompound(String key);

    int getInt(String key);

    Set<String> getKeys();

    ListTag getList(String key);

    String getString(String key);

    void set(String key, Object value);

    void setByte(String key, byte value);

    void setCompound(String key, CompoundTag value);

    void setInt(String key, int value);

    void setList(String key, ListTag value);

    void setString(String key, String value);
}
