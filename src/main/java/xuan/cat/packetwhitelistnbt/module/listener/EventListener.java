package xuan.cat.packetwhitelistnbt.module.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.MerchantRecipe;
import xuan.cat.packetwhitelistnbt.PacketWhitelistNbt;
import xuan.cat.packetwhitelistnbt.api.ServerInstance;
import xuan.cat.packetwhitelistnbt.api.ServerPacket;
import xuan.cat.packetwhitelistnbt.api.event.EntityEquipmentEvent;
import xuan.cat.packetwhitelistnbt.api.event.EntityMetadataEvent;
import xuan.cat.packetwhitelistnbt.api.event.OpenWindowMerchantEvent;
import xuan.cat.packetwhitelistnbt.api.event.RecipeUpdateEvent;
import xuan.cat.packetwhitelistnbt.api.event.SetSlotEvent;
import xuan.cat.packetwhitelistnbt.api.event.WindowItemsEvent;
import xuan.cat.packetwhitelistnbt.core.data.ConfigData;
import xuan.cat.packetwhitelistnbt.module.ReduceServer;

public final class EventListener implements Listener {
    private final ConfigData configData;
    private final ReduceServer reduceServer;
    private final ServerPacket serverPacket;
    private final ServerInstance serverInstance;

    public EventListener(ConfigData configData, ReduceServer reduceServer, ServerPacket serverPacket, ServerInstance serverInstance) {
        this.configData = configData;
        this.reduceServer = reduceServer;
        this.serverPacket = serverPacket;
        this.serverInstance = serverInstance;
    }

    /**
     * @param event 玩家登入
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void event(PlayerJoinEvent event) {
        // 注入代碼
        serverInstance.injectPlayer(event.getPlayer());
    }

    /**
     * @param event 玩家登出
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void on(PlayerQuitEvent event) {
        reduceServer.clearPermissions(event.getPlayer());
    }


    /**
     * @param event 設置欄位封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(SetSlotEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            serverPacket.convertSetSlot(event, configData::filtrationItem);
        }
    }

    /**
     * @param event 視窗物品封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(WindowItemsEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            serverPacket.convertWindowItems(event, configData::filtrationItem);
        }
    }

    /**
     * @param event 實體裝備封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(EntityEquipmentEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            serverPacket.convertEntityEquipment(event, configData::filtrationItem);
        }
    }

    /**
     * @param event 合成更新封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(RecipeUpdateEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            serverPacket.convertRecipeUpdate(event, recipe -> serverInstance.filtrationRecipe(recipe, configData::filtrationItem));
        }
    }

    /**
     * @param event 村民交易
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(OpenWindowMerchantEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            serverPacket.convertWindowMerchants(event, recipe -> (MerchantRecipe) serverInstance.filtrationRecipe(recipe, configData::filtrationItem));
        }
    }


    /**
     * @param event 實體資料封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(EntityMetadataEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            serverPacket.convertEntityMetadata(event, configData::filtrationItem);
        }
    }


    /**
     * @param event 玩家切換遊戲模式
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && event.getNewGameMode() == GameMode.CREATIVE) {
            Bukkit.getScheduler().runTask(PacketWhitelistNbt.get(), player::updateInventory);
        } else if (player.getGameMode() == GameMode.CREATIVE && event.getNewGameMode() != GameMode.CREATIVE) {
            Bukkit.getScheduler().runTask(PacketWhitelistNbt.get(), player::updateInventory);
        }
    }

}
