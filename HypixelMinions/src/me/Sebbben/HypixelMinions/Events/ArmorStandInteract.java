package me.Sebbben.HypixelMinions.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.Inventory;

public class ArmorStandInteract implements Listener {

    @EventHandler
    public static void onArmorStandClick(PlayerArmorStandManipulateEvent e) {
        e.getPlayer().sendMessage("You clicked an armorstand");
        if (e.getRightClicked().getCustomName().equals("SebbbenMinion")) {
            Inventory inv = MinionManager.getPlacedMinions().get(e.getRightClicked().getLocation()).getInventory();
            e.getPlayer().openInventory(inv);
            e.setCancelled(true);
        }
    }
}
