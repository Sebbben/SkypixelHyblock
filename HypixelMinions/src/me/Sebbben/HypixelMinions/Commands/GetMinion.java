package me.Sebbben.HypixelMinions.Commands;

import me.Sebbben.HypixelMinions.Items.MinionHeads;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetMinion implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            ((Player) sender).getInventory().addItem(MinionHeads.getMinionHead("Sebbben"));
            return true;
        }

        return true;
    }
}
