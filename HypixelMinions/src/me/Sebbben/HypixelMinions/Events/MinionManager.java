package me.Sebbben.HypixelMinions.Events;

import me.Sebbben.HypixelMinions.Items.MinionHeads;
import me.Sebbben.HypixelMinions.Main;
import me.Sebbben.HypixelMinions.Minions.Minion;
import me.Sebbben.HypixelMinions.Minions.SebbbenMinion;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;


public class MinionManager implements Listener {

    private static HashMap<UUID, Minion> placedMinions = new HashMap<>();
    private static Main plugin;

    public MinionManager(Main p) {
        plugin = p;
        MinionHeads.init();
        Minion.init();
    }

    public static void removeMinion(UUID uuid) {
        placedMinions.remove(uuid);
    }

    public static Minion getPlacedMinion(UUID uuid) {
        return placedMinions.get(uuid);
    }

    @EventHandler
    public static void onMinionPlace(BlockPlaceEvent e) {
        if (e.getItemInHand().isSimilar(MinionHeads.getMinionHead("Sebbben"))) {

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
        if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof Minion) {
            ((Minion) e.getClickedInventory().getHolder()).handleInventoryClick(e);
        }

    }

    @EventHandler
    public static void onArmorStandClick(PlayerArmorStandManipulateEvent e) {
        if (placedMinions.containsKey(e.getRightClicked().getUniqueId())) {
            Inventory inv = placedMinions.get(e.getRightClicked().getUniqueId()).getInventory();
            e.getPlayer().openInventory(inv);
            e.setCancelled(true);
        }
    }

}
