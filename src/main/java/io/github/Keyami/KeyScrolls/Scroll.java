package io.github.Keyami.KeyScrolls;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Scroll {

    public static ItemStack getScroll() {
        List<String> lore = new ArrayList<String>();
        ItemStack scroll = new ItemStack(Material.PAPER, 1);
        ItemMeta scrollMeta = scroll.getItemMeta();

        lore.add(ChatColor.GRAY + "An unbound teleportation scroll.");
        lore.add(ChatColor.GRAY + "Right-click to bind to your current location.");
        lore.add(ChatColor.WHITE + "10 " + ChatColor.GREEN + "uses remaining.");

        scrollMeta.setLore(lore);
        scrollMeta.setDisplayName("Unbound Teleportation Scroll");

        scroll.setItemMeta(scrollMeta);

        return scroll;
    }

}
