package xuan.cat.packetwhitelistnbt;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xuan.cat.packetwhitelistnbt.api.ServerInstance;
import xuan.cat.packetwhitelistnbt.api.ServerNBT;
import xuan.cat.packetwhitelistnbt.api.ServerPacket;
import xuan.cat.packetwhitelistnbt.module.listener.EventListener;
import xuan.cat.packetwhitelistnbt.module.ReduceServer;
import xuan.cat.packetwhitelistnbt.module.server.MinecraftServer;
import xuan.cat.packetwhitelistnbt.module.server.MinecraftNBT;
import xuan.cat.packetwhitelistnbt.module.server.MinecraftPacket;
import xuan.cat.packetwhitelistnbt.core.command.Command;
import xuan.cat.packetwhitelistnbt.core.command.CommandSuggest;
import xuan.cat.packetwhitelistnbt.core.data.ConfigData;

public final class PacketWhitelistNbt extends JavaPlugin {
    private static Plugin plugin;
    private static ReduceServer reduceServer;
    private static ConfigData configData;
    private static ServerNBT serverNBT;
    private static ServerPacket serverPacket;
    private static ServerInstance serverInstance;

    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        // 檢測版本
        String bukkitVersion = Bukkit.getBukkitVersion();
        if (bukkitVersion.matches("^1\\.19\\D.*$")) {
            // 1.19
            serverPacket = new MinecraftPacket();
            serverNBT = new MinecraftNBT();
            serverInstance = new MinecraftServer();
        } else {
            throw new IllegalArgumentException("Unsupported MC version: " + bukkitVersion);
        }

        configData = new ConfigData(this, getConfig(), serverNBT);
        reduceServer = new ReduceServer(configData, this);

        Bukkit.getPluginManager().registerEvents(new EventListener(configData, reduceServer, serverPacket, serverInstance), this);

        // 指令
        PluginCommand command = getCommand("whitelistnbt");
        if (command != null) {
            command.setExecutor(new Command(reduceServer, configData));
            command.setTabCompleter(new CommandSuggest());
        }
    }

    public static ReduceServer getReduceServer() {
        return reduceServer;
    }

    public static ConfigData getConfigData() {
        return configData;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public void onDisable() {
        if (reduceServer != null) {
            reduceServer.close();
        }
    }
}
