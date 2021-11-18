package me.Sebbben.HypixelMinions.Events;

import me.Sebbben.HypixelMinions.Items.MinionHeads;
import me.Sebbben.HypixelMinions.Main;
import me.Sebbben.HypixelMinions.Minions.Minion;
import me.Sebbben.HypixelMinions.Minions.SebbbenMinion;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;


public class MinionManager implements Listener {

    private static HashMap<Location, Minion> placedMinions = new HashMap<>();
    private static Main plugin;

    public MinionManager(Main p) {
        plugin = p;
        MinionHeads.init();
    }

    public static void removeMinion(Location l) {
        placedMinions.get(l).removeMinion();
        l.getWorld().dropItem(l,placedMinions.get(l).getMinionHead());
        placedMinions.remove(l);
    }

    public static HashMap<Location, Minion> getPlacedMinions() {
        return placedMinions;
    }

    @EventHandler
    public static void onMinionPlace(BlockPlaceEvent e) {
        ItemMeta eventMeta = e.getItemInHand().getItemMeta();
        ItemMeta minionMeta = MinionHeads.getMinionHead("Sebbben").getItemMeta();
        if (eventMeta.getDisplayName().equals(minionMeta.getDisplayName()) && // check item displayname
                eventMeta.getLore().toString().equals(minionMeta.getLore().toString())) { // Check item lore

            e.setCancelled(true);

            if (e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
                e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));

            Location targetLocation = e.getBlock().getLocation().add(0.5,0,0.5);
            targetLocation.setDirection(e.getPlayer().getLocation().getDirection().multiply(-1));

            placedMinions.put(targetLocation, new SebbbenMinion(plugin, targetLocation));
        }
    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof Minion) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.BEDROCK) {
                e.getWhoClicked().closeInventory();
                removeMinion(((Minion) e.getInventory().getHolder()).getLocation());
            }

        }

    }

}
