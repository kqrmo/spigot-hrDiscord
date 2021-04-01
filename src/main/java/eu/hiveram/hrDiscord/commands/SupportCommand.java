package eu.hiveram.hrDiscord.commands;

import eu.hiveram.hrDiscord.ConfigManager;
import eu.hiveram.hrDiscord.Webhook;
import eu.hiveram.hrDiscord.hrDiscord;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.IOException;

public class SupportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        FileConfiguration configFile = ConfigManager.getInstance().getConfig("config.yml");
        Boolean adminonline = false;
        Webhook wh = new Webhook(ConfigManager.getInstance().getStringRaw(configFile, "WebhookURL"));
        if (sender instanceof Player) {
            if (args.length == 0) {
                sender.sendMessage(hrDiscord.colorize(ConfigManager.getInstance().getStringRaw(configFile, "Messages.MessageMissing")));
            } else {
                sender.sendMessage(hrDiscord.colorize(ConfigManager.getInstance().getStringRaw(configFile, "Messages.MessageSent")
                ));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if(p.hasPermission("hrd.seemessages")) {
                        adminonline = true;
                        String msg = StringUtils.join(args, ' ', 0, args.length);

                        String staffFormat = ConfigManager.getInstance().getStringRaw(configFile, "Messages.StaffMessage")
                                            .replace("{player}", sender.getName())
                                            .replace("{message}", msg);
                        p.sendMessage(hrDiscord.colorize(staffFormat));
                        ;
                    }
                }
                if (adminonline == false) {
                    if (ConfigManager.getInstance().getBoolean(configFile, "Settings.MentionEveryone")) {
                        wh.setContent("@everyone");
                    }
                    wh.setUsername(sender.getName());
                    wh.setAvatarUrl("https://minotar.net/helm/" + sender.getName() + ".png");
                    wh.setTts(false);
                    sendEmbed(sender, args, configFile, wh);
                }
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&', ConfigManager.getInstance().getStringRaw(configFile, "Messages.MessageMissing")
                ));
            } else {
                wh.setUsername("CubeSupport");
                wh.setTts(false);
                wh.setAvatarUrl("https://minotar.net/avatar/console.png");
                if (ConfigManager.getInstance().getBoolean(configFile, "Settings.MentionEveryone")) {
                    wh.setContent("@everyone");
                }
                sendEmbed(sender, args, configFile, wh);
            }
        }
        return true;
    }

    private void sendEmbed(CommandSender sender, String[] args, FileConfiguration configFile, Webhook wh) {
        wh.addEmbed(new Webhook.EmbedObject()
                .setDescription(StringUtils.join(args, ' ', 0, args.length))
                .setColor(hrDiscord.hex2Rgb(ConfigManager.getInstance().getStringRaw(configFile, "Settings.Support")))
        );
        try {
            wh.execute();
            sender.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', ConfigManager.getInstance().getStringRaw(configFile, "Messages.MessageSent")
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
