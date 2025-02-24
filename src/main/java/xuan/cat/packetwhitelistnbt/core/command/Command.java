package xuan.cat.packetwhitelistnbt.core.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xuan.cat.packetwhitelistnbt.module.ReduceServer;
import xuan.cat.packetwhitelistnbt.core.data.ConfigData;

public final class Command implements CommandExecutor {
    private final ReduceServer reduceServer;
    private final ConfigData configData;

    public Command(ReduceServer reduceServer, ConfigData configData) {
        this.reduceServer   = reduceServer;
        this.configData     = configData;
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String message, String[] parameters) {
        if (!sender.hasPermission("command.whitelistnbt")) {
            // 無權限
            sender.sendMessage(ChatColor.RED + "No permission");
        } else {
            if (parameters.length < 1) {
                // 缺少參數
                sender.sendMessage(ChatColor.RED + "Missing parameters");
            } else {
                switch (parameters[0]) {
                    case "reload":
                        try {
                            configData.reload();
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.updateInventory();
                            }
                            // 重讀配置完成
                            sender.sendMessage(ChatColor.YELLOW + "Reread configuration successfully");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            // 重讀配置錯誤
                            sender.sendMessage(ChatColor.RED + "Reread configuration error");
                        }
                        break;
                    case "permissionCheck":
                        // 檢查玩家權限
                        if (parameters.length < 2) {
                            // 缺少參數
                            sender.sendMessage(ChatColor.RED + "Missing parameters");
                        } else {
                            Player player = Bukkit.getPlayer(parameters[1]);
                            if (player == null) {
                                // 玩家不存在
                                sender.sendMessage(ChatColor.RED + "Player does not exist");
                            } else {
                                reduceServer.getPermissions(player).permissionsNeed = true;
                                // 已重新檢查玩家權限
                                sender.sendMessage(ChatColor.YELLOW + "Rechecked player permissions");
                            }
                        }
                        break;
                    default:
                        // 未知的參數類型
                        sender.sendMessage(ChatColor.RED + "Unknown parameter type");
                        break;
                }
            }
        }
        return true;
    }
}
