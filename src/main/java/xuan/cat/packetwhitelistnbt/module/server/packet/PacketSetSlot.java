package xuan.cat.packetwhitelistnbt.module.server.packet;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;

import java.lang.reflect.Field;

public final class PacketSetSlot {
    private final ClientboundContainerSetSlotPacket packet;

    public PacketSetSlot(ClientboundContainerSetSlotPacket packet) {
        this.packet = packet;
    }

    private static Field field_itemStack;

    static {
        try {
            field_itemStack = ClientboundContainerSetSlotPacket.class.getDeclaredField("f"); // TODO 映射 itemStack
            field_itemStack.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public org.bukkit.inventory.ItemStack getItem() {
        try {
            return CraftItemStack.asBukkitCopy((ItemStack) field_itemStack.get(packet));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void setItem(org.bukkit.inventory.ItemStack item) {
        try {
            field_itemStack.set(packet, CraftItemStack.asNMSCopy(item));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
