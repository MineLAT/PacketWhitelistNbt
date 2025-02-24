package xuan.cat.packetwhitelistnbt.api;

import org.bukkit.inventory.ItemStack;
import xuan.cat.packetwhitelistnbt.api.nbt.CompoundTag;
import xuan.cat.packetwhitelistnbt.api.nbt.ListTag;

public interface ServerNBT {
    CompoundTag createCompound();

    ListTag createList();

    CompoundTag fromItem(ItemStack item);

    ItemStack toItem(CompoundTag nbt);
}
