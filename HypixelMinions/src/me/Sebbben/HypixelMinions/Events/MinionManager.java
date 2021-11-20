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
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;


public class MinionManager implements Listener {

    private static HashMap<UUID, Minion> placedMinions = new HashMap<>();
    private static Main plugin;

    public MinionManager(Main p) {
        plugin = p;
        MinionHeads.init();
    }

    public static void removeMinion(UUID uuid) {
        plugin.getServer().getEntity(uuid).getWorld().dropItem(placedMinions.get(uuid).getLocation(),placedMinions.get(uuid).getMinionHead());
        placedMinions.get(uuid).removeMinion();
        placedMinions.remove(uuid);
    }

    public static Minion getPlacedMinion(UUID uuid) {
        return placedMinions.get(uuid);
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

            SebbbenMinion minion = new SebbbenMinion(plugin, targetLocation);

            placedMinions.put(minion.getUUID(), minion);
        }
    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof Minion) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.BEDROCK) {
                e.getWhoClicked().closeInventory();
                removeMinion(((Minion) e.getInventory().getHolder()).getUUID());
            }

        }

    }

    @EventHandler
    public static void onArmorStandClick(PlayerArmorStandManipulateEvent e) {
        if (e.getRightClicked().getCustomName().equals("Sebbben Minion")) {
            Inventory inv = placedMinions.get(e.getRightClicked().getUniqueId()).getInventory();
            e.getPlayer().openInventory(inv);
            e.setCancelled(true);
        }
    }

}
