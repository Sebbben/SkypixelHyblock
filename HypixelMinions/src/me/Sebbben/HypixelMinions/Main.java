package me.Sebbben.HypixelMinions;

import me.Sebbben.HypixelMinions.Commands.GetMinion;
import me.Sebbben.HypixelMinions.CraftingRecipies.Recipies;
import me.Sebbben.HypixelMinions.Events.ArmorStandInteract;
import me.Sebbben.HypixelMinions.Events.MinionManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("getMinion").setExecutor(new GetMinion());
        this.getServer().getPluginManager().registerEvents(new MinionManager(this), this);
        this.getServer().getPluginManager().registerEvents(new ArmorStandInteract(), this);

        Recipies.makeRecipies(this);
    }

    @Override
    public void onDisable() {

    }
}
