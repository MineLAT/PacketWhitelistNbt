package xuan.cat.packetwhitelistnbt.api;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.event.*;
import xuan.cat.packetwhitelistnbt.api.event.EntityEquipmentEvent;
import xuan.cat.packetwhitelistnbt.api.event.EntityMetadataEvent;
import xuan.cat.packetwhitelistnbt.api.event.OpenWindowMerchantEvent;
import xuan.cat.packetwhitelistnbt.api.event.RecipeUpdateEvent;
import xuan.cat.packetwhitelistnbt.api.event.SetSlotEvent;
import xuan.cat.packetwhitelistnbt.api.event.WindowItemsEvent;

import java.util.function.Function;

public interface ServerPacket {
    void convertSetSlot(SetSlotEvent event, Function<ItemStack, ItemStack> convert);

    void convertWindowItems(WindowItemsEvent event, Function<ItemStack, ItemStack> convert);

    void convertWindowMerchants(OpenWindowMerchantEvent event, Function<MerchantRecipe, MerchantRecipe> convert);

    void convertEntityEquipment(EntityEquipmentEvent event, Function<ItemStack, ItemStack> convert);

    void convertRecipeUpdate(RecipeUpdateEvent event, Function<Recipe, Recipe> convert);

    void convertEntityMetadata(EntityMetadataEvent event, Function<ItemStack, ItemStack> convert);
}
