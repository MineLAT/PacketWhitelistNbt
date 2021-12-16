package xuan.cat.packetwhitelistnbt.code.branch.v15.nbt;

import net.minecraft.server.v1_15_R1.NBTBase;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

import java.util.Set;

public final class Branch_15_NBTCompound implements BranchNBTCompound {
    protected NBTTagCompound tag;

    public Branch_15_NBTCompound() {
        this.tag = new NBTTagCompound();
    }

    public Branch_15_NBTCompound(NBTTagCompound tag) {
        this.tag = tag;
    }


    public NBTTagCompound getNMSTag() {
        return this.tag;
    }


    public BranchNBTType getType(String key) {
        return Branch_15_NBTType.fromNMS(tag.e(key));
    }
    
    public int size() {
        return tag.e();
    }

    public Object get(String key) {
        NBTBase base = tag.get(key);
        if (base instanceof NBTTagCompound) {
            return new Branch_15_NBTCompound((NBTTagCompound) base);
        } else if (base instanceof NBTTagList) {
            return new Branch_15_NBTList((NBTTagList) base);
        } else {
            return base;
        }
    }

    public Set<String> getKeys() {
        return tag.getKeys();
    }

    public byte getByte(String key) {
        return tag.getByte(key);
    }

    public BranchNBTCompound getCompound(String key) {
        NBTTagCompound nbtTagCompound = tag.getCompound(key);
        if (nbtTagCompound.e() == 0) {
            nbtTagCompound = new NBTTagCompound();
            tag.set(key, nbtTagCompound);
        }

        return new Branch_15_NBTCompound(nbtTagCompound);
    }
    
    public int getInt(String key) {
        return tag.getInt(key);
    }
    
    public String getString(String key) {
        return tag.getString(key);
    }

    public BranchNBTList getList(String key) {
        NBTTagList nbtTagList = (NBTTagList) tag.get(key);
        if (nbtTagList == null || nbtTagList.size() == 0) {
            nbtTagList = new NBTTagList();
            tag.set(key, nbtTagList);
        }

        return new Branch_15_NBTList(nbtTagList);
    }

    public void set(String key, Object value) {
        if (value instanceof Branch_15_NBTCompound) {
            tag.set(key, ((Branch_15_NBTCompound) value).getNMSTag());
        } else if (value instanceof Branch_15_NBTList) {
            tag.set(key, ((Branch_15_NBTList) value).getNMSTag());
        } else {
            tag.set(key, (NBTBase) value);
        }
    }

    public void setByte(String key, byte value) {
        tag.setByte(key, value);
    }

    public void setCompound(String key, BranchNBTCompound value) {
        tag.set(key, ((Branch_15_NBTCompound) value).getNMSTag());
    }

    public void setInt(String key, int value) {
        tag.setInt(key, value);
    }

    public void setList(String key, BranchNBTList value) {
        tag.set(key, ((Branch_15_NBTList) value).getNMSTag());
    }

    public void setString(String key, String value) {
        tag.setString(key, value);
    }
}
