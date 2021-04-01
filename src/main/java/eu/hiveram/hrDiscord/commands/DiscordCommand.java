package eu.hiveram.hrDiscord.commands;

import eu.hiveram.hrDiscord.ConfigManager;
import eu.hiveram.hrDiscord.hrDiscord;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class DiscordCommand implements CommandExecutor {
    private FileConfiguration config = ConfigManager.getInstance().getConfig("config.yml");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("discord")) {
            sender.sendMessage(hrDiscord.colorize(ConfigManager.getInstance().getStringRaw(config, "Messages.DiscordInvite")));
        }

        return true;
    }
}
