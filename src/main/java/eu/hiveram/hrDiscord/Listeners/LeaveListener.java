package eu.hiveram.hrDiscord.Listeners;

import eu.hiveram.hrDiscord.ConfigManager;
import eu.hiveram.hrDiscord.Webhook;
import eu.hiveram.hrDiscord.hrDiscord;
import litebans.api.Entry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import litebans.api.*;

public class LeaveListener implements Listener {
    FileConfiguration configFile = ConfigManager.getInstance().getConfig("config.yml");
    Webhook wh = new Webhook(ConfigManager.getInstance().getStringRaw(configFile, "WebhookURL"));

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        String leaveFormat = ConfigManager.getInstance().getStringRaw(configFile, "Settings.Events.PlayerLeave")
                .replace("{player}", event.getPlayer().getName());
        wh.setUsername(ConfigManager.getInstance().getStringRaw(configFile, "Settings.Username"));
        wh.setAvatarUrl(ConfigManager.getInstance().getStringRaw(configFile, "Settings.Avatar"));
        wh.setTts(false);
        wh.addEmbed(new Webhook.EmbedObject()
                .setAuthor(leaveFormat, "", "https://minotar.net/helm/" + event.getPlayer().getName() + ".png")
                .setColor(hrDiscord.hex2Rgb(ConfigManager.getInstance().getStringRaw(configFile, "Settings.Colors.PlayerLeave")))
        );
        Bukkit.getScheduler().runTaskAsynchronously(hrDiscord.getPlugin(hrDiscord.class), () -> {
            try {
                wh.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
