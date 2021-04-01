package eu.hiveram.hrDiscord.Listeners;

import eu.hiveram.hrDiscord.hrDiscord;
import eu.hiveram.hrDiscord.ConfigManager;
import eu.hiveram.hrDiscord.Webhook;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;
import java.io.IOException;

public class DeathListener implements Listener {
    FileConfiguration configFile = ConfigManager.getInstance().getConfig("config.yml");
    Webhook wh = new Webhook(ConfigManager.getInstance().getStringRaw(configFile, "WebhookURL"));

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        wh.setUsername(ConfigManager.getInstance().getStringRaw(configFile, "Settings.Username"));
        wh.setAvatarUrl(ConfigManager.getInstance().getStringRaw(configFile, "Settings.Avatar"));
        wh.setTts(false);
        wh.addEmbed(new Webhook.EmbedObject()
                .setAuthor(event.getDeathMessage(), "", "https://minotar.net/helm/" + event.getEntity().getName() + ".png")
                .setColor(hrDiscord.hex2Rgb(ConfigManager.getInstance().getStringRaw(configFile, "Settings.Colors.PlayerDeath")))
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
