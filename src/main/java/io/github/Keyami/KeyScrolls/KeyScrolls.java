package io.github.Keyami.KeyScrolls;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import static io.github.Keyami.KeyScrolls.Scroll.getScroll;

public class KeyScrolls extends JavaPlugin {
    public static KeyScrolls pl;

    NamespacedKey scrollKey = new NamespacedKey(this, "scroll");

    @Override
    public void onEnable() {
        pl = this;
        super.onEnable();
        getServer().getPluginManager().registerEvents(new ScrollEventHandler(), this);
        pl.getLogger().info("KeyScrolls has been enabled!");

        //Scroll Recipe
        ShapedRecipe scrollRecipe = new ShapedRecipe(scrollKey, getScroll());
        scrollRecipe.shape(" E ", "EPE", " E ");
        scrollRecipe.setIngredient('E', Material.ENDER_PEARL);
        scrollRecipe.setIngredient('P', Material.PAPER);

        Bukkit.addRecipe(scrollRecipe);

    }

    @Override
    public void onDisable() {
        super.onDisable();

        Bukkit.removeRecipe(scrollKey);

        pl.getLogger().info("KeyScrolls has been disabled!");

    }

}
