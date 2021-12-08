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



    private static void makeMinionHeads() {
    }
}
