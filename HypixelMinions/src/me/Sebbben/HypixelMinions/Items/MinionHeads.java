package me.Sebbben.HypixelMinions.Items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MinionHeads {

    private static HashMap<String, ItemStack> minionHeads = new HashMap<>();

    public static HashMap<String, ItemStack> getMinionHeads() {
        return minionHeads;
    }

    public static ItemStack getMinionHead(String head) {
        return minionHeads.get(head);
    }

    public static void init() {
        makeMinionHeads();
    }

    private static ItemStack newHead(OfflinePlayer player,String title, List<String> lore) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD,1);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(player);
        headMeta.setLore(lore);
        headMeta.setDisplayName(title);
        head.setItemMeta(headMeta);
        return head;
    }

    private static void makeMinionHeads() {
        minionHeads.put("Sebbben", newHead((OfflinePlayer) Bukkit.getPlayer("Sebbben"), "SebbbenMinion",Arrays.asList("Sebbben minion's head", "Used to place the Sebbben minion")));
    }
}
