package xuan.cat.packetwhitelistnbt.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public final class OpenWindowMerchantEvent extends PacketEvent {
    private static final HandlerList handlers = new HandlerList();
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Object packet;

    public OpenWindowMerchantEvent(Player player, Object packet) {
        super(player);
        this.packet = packet;
    }

    public Object getPacket() {
        return packet;
    }
}