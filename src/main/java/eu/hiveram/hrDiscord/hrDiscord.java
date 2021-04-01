package eu.hiveram.hrDiscord;

import eu.hiveram.hrDiscord.Listeners.ChatListener;
import eu.hiveram.hrDiscord.Listeners.DeathListener;
import eu.hiveram.hrDiscord.Listeners.JoinListener;
import eu.hiveram.hrDiscord.Listeners.LeaveListener;
import eu.hiveram.hrDiscord.commands.DiscordCommand;
import eu.hiveram.hrDiscord.commands.MainCommand;
import eu.hiveram.hrDiscord.commands.SupportCommand;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;

public class hrDiscord extends JavaPlugin {


    @Override
    public void onEnable() {
        ConfigManager.getInstance().setPlugin(this);
        ConfigManager.getInstance().getConfig("config.yml");

        getCommand("hrd").setExecutor(new MainCommand());
        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("support").setExecutor(new SupportCommand());

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);


        getLogger().info(colorize("&a> &7hrDiscord enabled!"));
        checkUrl();
        ServerUP();
    }

    @Override
    public void onDisable() {
        getLogger().info(colorize("&c> &7hrDiscord disabled!"));
        ServerDown();
    }

    private void checkUrl() {
        if (ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "WebhookURL").equals("")) {
            getLogger().info(colorize("&c> &7PLEASE CHECK YOUR CONFIG.YML FOR WEBHOOK URL!"));
        }
    }

    public static String colorize(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void ServerUP() {
        Webhook wh = new Webhook(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "WebhookURL"));
        wh.setUsername(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "Settings.Username"));
        wh.setAvatarUrl(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "Settings.Avatar"));
        wh.setTts(false);
        wh.addEmbed(new Webhook.EmbedObject()
            .setAuthor(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "Settings.Events.ServerStart" ), "", "")
            .setColor(hex2Rgb(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "Settings.Colors.ServerStart")))
        );
        try {
            wh.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ServerDown() {
        Webhook wh = new Webhook(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "WebhookURL"));
        wh.setUsername(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "Settings.Username"));
        wh.setAvatarUrl(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "Settings.Avatar"));
        wh.setTts(false);
        wh.addEmbed(new Webhook.EmbedObject()
                .setAuthor(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "Settings.Events.ServerStop" ), "", "")
                .setColor(hex2Rgb(ConfigManager.getInstance().getStringRaw(ConfigManager.getInstance().getConfig("config.yml"), "Settings.Colors.ServerStop")))
        );
        try {
            wh.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Color hex2Rgb(String hex) {
        hex = hex.replace("#", "");
        switch (hex.length()) {
            case 6:
                return new Color(
                        Integer.valueOf(hex.substring(0, 2), 16),
                        Integer.valueOf(hex.substring(2, 4), 16),
                        Integer.valueOf(hex.substring(4, 6), 16));
            case 8:
                return new Color(
                        Integer.valueOf(hex.substring(0, 2), 16),
                        Integer.valueOf(hex.substring(2, 4), 16),
                        Integer.valueOf(hex.substring(4, 6), 16),
                        Integer.valueOf(hex.substring(6, 8), 16));
        }
        return null;
    }

}
