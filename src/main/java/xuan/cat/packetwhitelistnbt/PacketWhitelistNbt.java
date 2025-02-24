package xuan.cat.packetwhitelistnbt;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xuan.cat.packetwhitelistnbt.api.ServerInstance;
import xuan.cat.packetwhitelistnbt.api.ServerNBT;
import xuan.cat.packetwhitelistnbt.api.ServerPacket;
import xuan.cat.packetwhitelistnbt.core.command.Command;
import xuan.cat.packetwhitelistnbt.core.command.CommandSuggest;
import xuan.cat.packetwhitelistnbt.core.data.ConfigData;
import xuan.cat.packetwhitelistnbt.module.ReduceServer;
import xuan.cat.packetwhitelistnbt.module.listener.EventListener;
import xuan.cat.packetwhitelistnbt.module.server.MinecraftNBT;
import xuan.cat.packetwhitelistnbt.module.server.MinecraftPacket;
import xuan.cat.packetwhitelistnbt.module.server.MinecraftServer;

public final class PacketWhitelistNbt extends JavaPlugin {

    private static PacketWhitelistNbt instance;

    @NotNull
    public static PacketWhitelistNbt get() {
        return instance;
    }

    private final ServerNBT serverNBT;
    private final ServerPacket serverPacket;
    private final ServerInstance serverInstance;

    private ReduceServer reduceServer;
    private ConfigData configData;

    public PacketWhitelistNbt() {
        instance = this;
        serverPacket = new MinecraftPacket();
        serverNBT = new MinecraftNBT();
        serverInstance = new MinecraftServer();
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        configData = new ConfigData(this, getConfig(), serverNBT);
        reduceServer = new ReduceServer(configData, this);

        Bukkit.getPluginManager().registerEvents(new EventListener(configData, reduceServer, serverPacket, serverInstance), this);

        // Register command
        PluginCommand command = getCommand("whitelistnbt");
        if (command != null) {
            command.setExecutor(new Command(reduceServer, configData));
            command.setTabCompleter(new CommandSuggest());
        }
    }

    @Override
    public void onDisable() {
        if (reduceServer != null) {
            reduceServer.close();
        }
    }

    @NotNull
    public ReduceServer getReduceServer() {
        return reduceServer;
    }

    @NotNull
    public ConfigData getConfigData() {
        return configData;
    }
}
