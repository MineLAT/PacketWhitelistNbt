package xuan.cat.packetwhitelistnbt.module.server.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import xuan.cat.packetwhitelistnbt.api.nbt.ListTag;
import xuan.cat.packetwhitelistnbt.api.nbt.TagType;

public final class MinecraftListTag extends ListTag {
    protected net.minecraft.nbt.ListTag tag;

    public MinecraftListTag() {
        this.tag = new net.minecraft.nbt.ListTag();
    }

    public MinecraftListTag(net.minecraft.nbt.ListTag tag) {
        this.tag = tag;
    }


    public net.minecraft.nbt.ListTag getNMSTag() {
        return this.tag;
    }


    public TagType getOwnType() {
        return MinecraftTags.fromNMS(tag.getElementType());
    }

    public boolean add(Object value) {
        add(size(), value);
        return true;
    }
    public void add(int index, Object value) {
        if (value instanceof MinecraftCompoundTag) {
            tag.add(index, ((MinecraftCompoundTag) value).getNMSTag());
        } else if (value instanceof MinecraftListTag) {
            tag.add(index, ((MinecraftListTag) value).getNMSTag());
        } else {
            tag.add(index, (Tag) value);
        }
    }

    public Object get(int index) {
        Tag base = tag.get(index);
        if (base instanceof CompoundTag) {
            return new MinecraftCompoundTag((CompoundTag) base);
        } else if (base instanceof net.minecraft.nbt.ListTag) {
            return new MinecraftListTag((net.minecraft.nbt.ListTag) base);
        } else {
            return base;
        }
    }

    public int size() {
        return tag.size();
    }
}
