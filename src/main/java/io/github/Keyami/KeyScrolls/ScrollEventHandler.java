package io.github.Keyami.KeyScrolls;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.Keyami.KeyScrolls.Scroll.getScroll;

public class ScrollEventHandler implements Listener {

    String scrollName = "";

    @EventHandler(priority = EventPriority.HIGH)
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        List<String> lore = new ArrayList<String>();
        ItemMeta handMeta = p.getInventory().getItemInMainHand().getItemMeta();

        if (!(handMeta == null)) {
            if (handMeta.equals(getScroll().getItemMeta())) {
                Bukkit.broadcastMessage("DEBUG: Passed check for UNBOUND scroll!");
                ItemStack scroll = p.getInventory().getItemInMainHand();
                ItemMeta scrollMeta = p.getInventory().getItemInMainHand().getItemMeta();
                Inventory AnvilRename = Bukkit.createInventory(null, InventoryType.ANVIL);
                AnvilRename.addItem(scroll);

                p.openInventory(AnvilRename);

                scrollMeta.setDisplayName(scrollName);
                scrollMeta.setLore(Arrays.asList(ChatColor.GRAY + "A bound teleportation scroll.", ChatColor.GRAY + "Right-click to teleport to the bound location.",
                        ChatColor.BLUE + "X: " + Math.round(p.getLocation().getX()) + " Y: " + Math.round(p.getLocation().getY()) + " Z: " + Math.round(p.getLocation().getZ()),
                        ChatColor.WHITE + " 10 " + ChatColor.GREEN + "uses remaining."));
                scroll.setItemMeta(scrollMeta);

            }
            if (handMeta.hasLore() && (!(handMeta.getLore().isEmpty()))) {
                if (handMeta.getLore().get(0).equals(ChatColor.GRAY + "A bound teleportation scroll.")) {
                    Bukkit.broadcastMessage("DEBUG: Passed check for BOUND scroll!");
                    String coords = ChatColor.stripColor(handMeta.getLore().get(2).replaceAll("[[a-zA-Z]+:]", ""));

                    String[] fullCoords = coords.split(" {2}");
                    String x = fullCoords[0].replace(" ", "");

                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 3));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 3));

                    Location teleportLoc = new Location(p.getWorld(), Integer.parseInt(x), Integer.parseInt(fullCoords[1]), Integer.parseInt(fullCoords[2]));

                    ItemMeta newMeta = p.getInventory().getItemInMainHand().getItemMeta();
                    int uses = Integer.parseInt(ChatColor.stripColor(handMeta.getLore().get(3).replaceAll("[a-zA-Z+:. §]", "")));

                    if (uses > 1) {
                        newMeta.setLore(Arrays.asList(ChatColor.GRAY + "A bound teleportation scroll.", ChatColor.GRAY + "Right-click to teleport to the bound location.",
                                ChatColor.BLUE + "X: " + Integer.parseInt(x) + " Y: " + Integer.parseInt(fullCoords[1]) + " Z: " + Integer.parseInt(fullCoords[2]),
                                ChatColor.WHITE + String.valueOf(uses - 1) + ChatColor.GREEN + " uses remaining."));
                        p.getInventory().getItemInMainHand().setItemMeta(newMeta);
                        p.teleport(teleportLoc);

                    } else if (uses == 1) {
                        p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                        p.teleport(teleportLoc);
                    }
                }
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onRename(PrepareAnvilEvent e) {
        if (e.getInventory().getItem(0).equals(getScroll())) {
            scrollName = e.getInventory().getRenameText();
        }
    }
}
