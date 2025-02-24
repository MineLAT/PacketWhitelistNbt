package xuan.cat.packetwhitelistnbt.api.nbt;

import java.util.AbstractList;

public abstract class ListTag extends AbstractList<Object> implements Tag {
    public abstract TagType getOwnType();

    public abstract boolean add(Object value);
}
