package eu.hiveram.hrDiscord.commands;

import eu.hiveram.hrDiscord.ConfigManager;
import eu.hiveram.hrDiscord.hrDiscord;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MainCommand implements CommandExecutor {

    private FileConfiguration config = ConfigManager.getInstance().getConfig("config.yml");
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("hrd")) {
            if (args.length == 0) {
                StringBuilder mainCommandMessage = new StringBuilder();
                mainCommandMessage.append("&fhrDiscord v1.0");
                if (sender.hasPermission("hrd.reload")) {
                    mainCommandMessage.append("\n" + ConfigManager.getInstance().getStringRaw(config, "Messages.CommandUsage"));
                }
                sender.sendMessage(hrDiscord.colorize(mainCommandMessage.toString()));
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("hrd.reload")) {
                    ConfigManager.getInstance().reloadConfigs();
                    sender.sendMessage(hrDiscord.colorize(ConfigManager.getInstance().getStringRaw(config, "Messages.ReloadSuccessful")));
                } else {
                    sender.sendMessage(hrDiscord.colorize(ConfigManager.getInstance().getStringRaw(config, "Messages.NoPermissions")));
                }
            }
        }

        return true;
    }
}
