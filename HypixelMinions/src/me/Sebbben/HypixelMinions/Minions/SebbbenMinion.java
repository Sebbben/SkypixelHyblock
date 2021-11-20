package me.Sebbben.HypixelMinions.Minions;

import me.Sebbben.HypixelMinions.Items.MinionHeads;
import me.Sebbben.HypixelMinions.Main;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SebbbenMinion extends Minion {


    public SebbbenMinion(Main plugin1, Location l) {
        super(plugin1, l);
    }

    @Override
    public String getMinionName() {
        return "Sebbben Minion";
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
    public Long getMiningPeriod() {
        return 1L;
    }

    @Override
    public ItemStack getMinionHead() {
        return MinionHeads.getMinionHead("Sebbben");
    }


}
