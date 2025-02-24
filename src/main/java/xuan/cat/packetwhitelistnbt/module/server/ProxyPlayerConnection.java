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
import xuan.cat.packetwhitelistnbt.api.event.EntityEquipmentEvent;
import xuan.cat.packetwhitelistnbt.api.event.EntityMetadataEvent;
import xuan.cat.packetwhitelistnbt.api.event.OpenWindowMerchantEvent;
import xuan.cat.packetwhitelistnbt.api.event.RecipeUpdateEvent;
import xuan.cat.packetwhitelistnbt.api.event.SetSlotEvent;
import xuan.cat.packetwhitelistnbt.api.event.WindowItemsEvent;

public final class ProxyPlayerConnection {
    public static boolean read(Player player, Packet<?> packet) {
        return true;
    }


    public static boolean write(Player player, Packet<?> packet) {
        try {
            if (packet instanceof ClientboundContainerSetSlotPacket) {
                SetSlotEvent event = new SetSlotEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundContainerSetContentPacket) {
                WindowItemsEvent event = new WindowItemsEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundSetEquipmentPacket) {
                EntityEquipmentEvent event = new EntityEquipmentEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundUpdateRecipesPacket) {
                RecipeUpdateEvent event = new RecipeUpdateEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundSetEntityDataPacket) {
                EntityMetadataEvent event = new EntityMetadataEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundMerchantOffersPacket) {
                OpenWindowMerchantEvent event = new OpenWindowMerchantEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
    }
}
