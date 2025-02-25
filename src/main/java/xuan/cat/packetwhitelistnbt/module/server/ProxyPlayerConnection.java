package xuan.cat.packetwhitelistnbt.module.server;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import xuan.cat.packetwhitelistnbt.api.event.EntityEquipmentEvent;
import xuan.cat.packetwhitelistnbt.api.event.EntityMetadataEvent;
import xuan.cat.packetwhitelistnbt.api.event.OpenWindowMerchantEvent;
import xuan.cat.packetwhitelistnbt.api.event.PacketEvent;
import xuan.cat.packetwhitelistnbt.api.event.RecipeUpdateEvent;
import xuan.cat.packetwhitelistnbt.api.event.SetSlotEvent;
import xuan.cat.packetwhitelistnbt.api.event.WindowItemsEvent;

public final class ProxyPlayerConnection {

    @Nullable
    public static Packet<?> read(Player player, Packet<?> packet) {
        return packet;
    }

    @Nullable
    public static Packet<?> write(Player player, Packet<?> packet) {
        final PacketEvent event;
        if (packet instanceof ClientboundContainerSetSlotPacket) {
            event = new SetSlotEvent(player, packet);
        } else if (packet instanceof ClientboundContainerSetContentPacket) {
            event = new WindowItemsEvent(player, packet);
        } else if (packet instanceof ClientboundSetEquipmentPacket) {
            event = new EntityEquipmentEvent(player, packet);
        } else if (packet instanceof ClientboundUpdateRecipesPacket) {
            event = new RecipeUpdateEvent(player, packet);
        } else if (packet instanceof ClientboundSetEntityDataPacket) {
            event = new EntityMetadataEvent(player, packet);
        } else if (packet instanceof ClientboundMerchantOffersPacket) {
            event = new OpenWindowMerchantEvent(player, packet);
        } else {
            return packet;
        }

        try {
            Bukkit.getPluginManager().callEvent(event);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (event.isCancelled()) {
            return null;
        }
        try {
            return (Packet<?>) event.getPacket();
        } catch (ClassCastException e) {
            return packet;
        }
    }
}
