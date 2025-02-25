package xuan.cat.packetwhitelistnbt.api.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class PacketEvent extends Event implements Cancellable {
    private final Player player;
    private Object packet;
    private boolean cancel = false;

    public PacketEvent(Player player, Object packet) {
        super(!Bukkit.isPrimaryThread());
        this.player = player;
        this.packet = packet;
    }

    public final boolean isCancelled() {
        return cancel;
    }

    public final void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public final Player getPlayer() {
        return player;
    }

    public Object getPacket() {
        return packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }
}