package me.Sebbben.HypixelMinions.CraftingRecipies;

import me.Sebbben.HypixelMinions.Items.MinionHeads;
import me.Sebbben.HypixelMinions.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class Recipies {

    public static void makeRecipies(Main plugin) {
        NamespacedKey nsk;
        if (NamespacedKey.fromString("sebbbenMinionRecipe") == null) {
            nsk = new NamespacedKey(plugin, "sebbbenMinionRecipe");
        } else {
            nsk = NamespacedKey.fromString("sebbbenMinionRecipe");
        }
        ShapedRecipe sebbbenMinionRecipe = new ShapedRecipe(nsk, MinionHeads.getMinionHead("Sebbben"));

        sebbbenMinionRecipe.shape("***","*#*","***");

        //sebbbenMinionRecipe.setIngredient('*', new RecipeChoice.ExactChoice(new ItemStack(Material.DIAMOND_BLOCK,64)));
        //sebbbenMinionRecipe.setIngredient('#', new RecipeChoice.ExactChoice(new ItemStack(Material.COAL_BLOCK,1)));

        sebbbenMinionRecipe.setIngredient('*', Material.DIAMOND_BLOCK);
        sebbbenMinionRecipe.setIngredient('#', Material.COAL_BLOCK);

        plugin.getServer().addRecipe(sebbbenMinionRecipe);

    }
}
