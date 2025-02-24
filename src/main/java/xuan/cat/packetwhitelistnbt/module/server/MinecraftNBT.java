package xuan.cat.packetwhitelistnbt.module.server;

import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import xuan.cat.packetwhitelistnbt.api.ServerNBT;
import xuan.cat.packetwhitelistnbt.api.nbt.CompoundTag;
import xuan.cat.packetwhitelistnbt.api.nbt.ListTag;
import xuan.cat.packetwhitelistnbt.module.server.nbt.MinecraftCompoundTag;
import xuan.cat.packetwhitelistnbt.module.server.nbt.MinecraftListTag;

import java.lang.reflect.Field;

public final class MinecraftNBT implements ServerNBT {
    @Override
    public CompoundTag createCompound() {
        return new MinecraftCompoundTag();
    }

    @Override
    public ListTag createList() {
        return new MinecraftListTag();
    }


    private static Field field_CraftItemStack_handle;
    static {
        try {
            field_CraftItemStack_handle = CraftItemStack.class.getDeclaredField("handle");
            field_CraftItemStack_handle.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public CompoundTag fromItem(org.bukkit.inventory.ItemStack item) {
        if (!(item instanceof CraftItemStack))
            item = CraftItemStack.asCraftCopy(item);
        MinecraftCompoundTag nbt = new MinecraftCompoundTag();
        try {
            ((ItemStack) field_CraftItemStack_handle.get(item)).save(nbt.getNMSTag());
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        nbt.setInt("version", CraftMagicNumbers.INSTANCE.getDataVersion());
        return nbt;
    }

    @Override
    public org.bukkit.inventory.ItemStack toItem(CompoundTag nbt) {
        ItemStack item = ItemStack.of(((MinecraftCompoundTag) nbt).getNMSTag());
        item.convertStack(nbt.getInt("version"));
        return CraftItemStack.asBukkitCopy(item);
    }
}
