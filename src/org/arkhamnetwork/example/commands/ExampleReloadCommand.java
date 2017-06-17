package org.arkhamnetwork.example.commands;

import org.arkhamnetwork.example.ArkhamExample;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ExampleReloadCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        ArkhamExample.getInstance().reloadConfig();
        return false;
    }

}
