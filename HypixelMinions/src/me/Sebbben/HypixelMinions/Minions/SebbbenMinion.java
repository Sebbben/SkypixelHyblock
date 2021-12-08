package me.Sebbben.HypixelMinions.Minions;

import me.Sebbben.HypixelMinions.Items.MinionHeads;
import me.Sebbben.HypixelMinions.Main;
import me.Sebbben.HypixelMinions.RomanNumeralGen;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SebbbenMinion extends Minion {

    public SebbbenMinion(int level, Main plugin1, Location l) {
        super(level, plugin1, l);
    }

    @Override
    public String getMinionName() {
        return "Sebbben Minion " + RomanNumeralGen.toRoman(minionLevel+1);
    }

    @Override
    public Material getToolType() {
        return Material.DIAMOND_PICKAXE;
    }

    @Override
    public Color getClothingColor() {
        return Color.AQUA;
    }

    @Override
    public Material getBlockType() {
        return Material.DIAMOND_BLOCK;
    }

    @Override
    public Material getMaterialType() {
        return Material.DIAMOND;
    }

    @Override
    public ItemStack getIdealLayoutItem() {
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("Air");
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public HashMap<Integer, Long> getMiningPeriod() {
        HashMap<Integer, Long> miningPeriod = new HashMap<>();
        miningPeriod.put(0,15L);
        miningPeriod.put(1,14L);
        miningPeriod.put(2,13L);
        miningPeriod.put(3,12L);
        miningPeriod.put(4,11L);
        miningPeriod.put(5,10L);
        miningPeriod.put(6,9L);
        miningPeriod.put(7,8L);
        miningPeriod.put(8,7L);
        miningPeriod.put(9,6L);
        miningPeriod.put(10,1L);
        return miningPeriod;
    }

    @Override
    public HashMap<Integer, Integer> getInventorySizes() {
        HashMap<Integer, Integer> inventorySlots = new HashMap<>();
        inventorySlots.put(0,1);
        inventorySlots.put(1,3);
        inventorySlots.put(2,3);
        inventorySlots.put(3,6);
        inventorySlots.put(4,6);
        inventorySlots.put(5,9);
        inventorySlots.put(6,9);
        inventorySlots.put(7,12);
        inventorySlots.put(8,12);
        inventorySlots.put(9,15);
        inventorySlots.put(10,15);
        return inventorySlots;
    }

    @Override
    public List<String> getHeadLore() {
        return Arrays.asList(
                "Sebbben minion head",
                "Used to place Sebbben minion");
    }

    @Override
    public Player getHeadOwner() {
        return Bukkit.getPlayer("Sebbben");
    }


}
