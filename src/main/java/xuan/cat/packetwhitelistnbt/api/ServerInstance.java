package xuan.cat.packetwhitelistnbt.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.function.Function;

public interface ServerInstance {
    Recipe filtrationRecipe(Recipe recipe, Function<ItemStack, ItemStack> convert);

    void injectPlayer(Player player);
}
