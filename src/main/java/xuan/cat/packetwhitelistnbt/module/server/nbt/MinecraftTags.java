package xuan.cat.packetwhitelistnbt.module.server.nbt;

import xuan.cat.packetwhitelistnbt.api.nbt.TagType;

public interface MinecraftTags {
    static TagType fromNMS(int type) {
        switch (type) {
            case 9:
                return TagType.NBT_LIST;
            case 10:
                return TagType.NBT_COMPOUND;
            default:
                return TagType.OTHER;
        }
    }
}
