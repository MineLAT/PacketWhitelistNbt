package xuan.cat.packetwhitelistnbt.module.server.nbt;

import net.minecraft.nbt.Tag;
import xuan.cat.packetwhitelistnbt.api.nbt.CompoundTag;
import xuan.cat.packetwhitelistnbt.api.nbt.ListTag;
import xuan.cat.packetwhitelistnbt.api.nbt.TagType;

import java.util.Set;

public final class MinecraftCompoundTag implements CompoundTag {
    protected net.minecraft.nbt.CompoundTag tag;

    public MinecraftCompoundTag() {
        this.tag = new net.minecraft.nbt.CompoundTag();
    }

    public MinecraftCompoundTag(net.minecraft.nbt.CompoundTag tag) {
        this.tag = tag;
    }


    public net.minecraft.nbt.CompoundTag getNMSTag() {
        return this.tag;
    }

    public TagType getType(String key) {
        return MinecraftTags.fromNMS(tag.getTagType(key));
    }

    public int size() {
        return tag.size();
    }

    public Object get(String key) {
        Tag base = tag.get(key);
        if (base instanceof net.minecraft.nbt.CompoundTag) {
            return new MinecraftCompoundTag((net.minecraft.nbt.CompoundTag) base);
        } else if (base instanceof net.minecraft.nbt.ListTag) {
            return new MinecraftListTag((net.minecraft.nbt.ListTag) base);
        } else {
            return base;
        }
    }

    public Set<String> getKeys() {
        return tag.getAllKeys();
    }

    public byte getByte(String key) {
        return tag.getByte(key);
    }

    public CompoundTag getCompound(String key) {
        net.minecraft.nbt.CompoundTag nbtTagCompound = tag.getCompound(key);
        if (nbtTagCompound.size() == 0) {
            nbtTagCompound = new net.minecraft.nbt.CompoundTag();
            tag.put(key, nbtTagCompound);
        }

        return new MinecraftCompoundTag(nbtTagCompound);
    }

    public int getInt(String key) {
        return tag.getInt(key);
    }

    public String getString(String key) {
        return tag.getString(key);
    }

    public ListTag getList(String key) {
        net.minecraft.nbt.ListTag ListTag = (net.minecraft.nbt.ListTag) tag.get(key);
        if (ListTag == null || ListTag.size() == 0) {
            ListTag = new net.minecraft.nbt.ListTag();
            tag.put(key, ListTag);
        }

        return new MinecraftListTag(ListTag);
    }

    public void set(String key, Object value) {
        if (value instanceof MinecraftCompoundTag) {
            tag.put(key, ((MinecraftCompoundTag) value).getNMSTag());
        } else if (value instanceof MinecraftListTag) {
            tag.put(key, ((MinecraftListTag) value).getNMSTag());
        } else {
            tag.put(key, (Tag) value);
        }
    }

    public void setByte(String key, byte value) {
        tag.putByte(key, value);
    }

    public void setCompound(String key, CompoundTag value) {
        tag.put(key, ((MinecraftCompoundTag) value).getNMSTag());
    }

    public void setInt(String key, int value) {
        tag.putInt(key, value);
    }

    public void setList(String key, ListTag value) {
        tag.put(key, ((MinecraftListTag) value).getNMSTag());
    }

    public void setString(String key, String value) {
        tag.putString(key, value);
    }
}
