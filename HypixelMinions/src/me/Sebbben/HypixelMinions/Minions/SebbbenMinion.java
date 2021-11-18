package me.Sebbben.HypixelMinions.Minions;

import me.Sebbben.HypixelMinions.Items.MinionHeads;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SebbbenMinion extends Minion {

    public SebbbenMinion(Location l) {
        super(l);
    }

    @Override
    public String getMinionName() {
        return "SebbbenMinion";
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
    public ItemStack getMinionHead() {
        return MinionHeads.getMinionHead("Sebbben");
    }


}
