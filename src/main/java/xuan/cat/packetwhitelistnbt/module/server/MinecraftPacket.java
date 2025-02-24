package xuan.cat.packetwhitelistnbt.module.server;

import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.ServerPacket;
import xuan.cat.packetwhitelistnbt.api.event.EntityEquipmentEvent;
import xuan.cat.packetwhitelistnbt.api.event.EntityMetadataEvent;
import xuan.cat.packetwhitelistnbt.api.event.OpenWindowMerchantEvent;
import xuan.cat.packetwhitelistnbt.api.event.RecipeUpdateEvent;
import xuan.cat.packetwhitelistnbt.api.event.SetSlotEvent;
import xuan.cat.packetwhitelistnbt.api.event.WindowItemsEvent;
import xuan.cat.packetwhitelistnbt.module.server.packet.PacketEntityEquipment;
import xuan.cat.packetwhitelistnbt.module.server.packet.PacketOpenWindowMerchant;
import xuan.cat.packetwhitelistnbt.module.server.packet.PacketRecipeUpdate;
import xuan.cat.packetwhitelistnbt.module.server.packet.PacketSetSlot;
import xuan.cat.packetwhitelistnbt.module.server.packet.PacketWindowItems;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class MinecraftPacket implements ServerPacket {
    @Override
    public void convertSetSlot(SetSlotEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        PacketSetSlot packet = new PacketSetSlot((ClientboundContainerSetSlotPacket) event.getPacket());
        packet.setItem(convert.apply(packet.getItem()));
    }

    @Override
    public void convertWindowItems(WindowItemsEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        PacketWindowItems packet = new PacketWindowItems((ClientboundContainerSetContentPacket) event.getPacket());
        List<org.bukkit.inventory.ItemStack> list = new ArrayList<>();
        packet.getItemList().forEach((item) -> list.add(convert.apply(item)));
        packet.setItemList(list);
        packet.setCarriedItem(convert.apply(packet.getCarriedItem()));
    }

    @Override
    public void convertWindowMerchants(OpenWindowMerchantEvent event, Function<MerchantRecipe, MerchantRecipe> convert) {
        PacketOpenWindowMerchant packet = new PacketOpenWindowMerchant((ClientboundMerchantOffersPacket) event.getPacket());
        List<MerchantRecipe> list = new ArrayList<>();
        packet.getRecipeList().forEach((item) -> list.add(convert.apply(item)));
        packet.setRecipeList(list);
    }

    @Override
    public void convertEntityEquipment(EntityEquipmentEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        PacketEntityEquipment packet = new PacketEntityEquipment((ClientboundSetEquipmentPacket) event.getPacket());
        Map<EquipmentSlot, org.bukkit.inventory.ItemStack> map = new HashMap<>();
        packet.getEquipmentItemMap().forEach((equipmentSlot, item) -> map.put(equipmentSlot, convert.apply(item)));
        packet.setEquipmentItemMap(map);
    }

    @Override
    public void convertRecipeUpdate(RecipeUpdateEvent event, Function<Recipe, Recipe> convert) {
        PacketRecipeUpdate packet = new PacketRecipeUpdate((ClientboundUpdateRecipesPacket) event.getPacket());
        List<Recipe> list = new ArrayList<>();
        packet.getRecipeList().forEach((item) -> list.add(convert.apply(item)));
        packet.setRecipeList(list);
    }

    private static Field field_DataValue_value;

    static {
        try {
            field_DataValue_value = SynchedEntityData.DataValue.class.getDeclaredField("c"); // value
            field_DataValue_value.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void convertEntityMetadata(EntityMetadataEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        ClientboundSetEntityDataPacket packet = (ClientboundSetEntityDataPacket) event.getPacket();
        packet.packedItems().forEach(entry -> {
            if (entry.serializer() == EntityDataSerializers.ITEM_STACK) {
                SynchedEntityData.DataValue<ItemStack> dataWatcher = (SynchedEntityData.DataValue<ItemStack>) entry;
                try {
                    field_DataValue_value.set(dataWatcher, CraftItemStack.asNMSCopy(convert.apply(CraftItemStack.asBukkitCopy(dataWatcher.value()))));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
