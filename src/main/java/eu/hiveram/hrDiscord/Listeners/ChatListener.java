package eu.hiveram.hrDiscord.Listeners;

import eu.hiveram.hrDiscord.ConfigManager;
import eu.hiveram.hrDiscord.Webhook;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class ChatListener implements Listener {
    FileConfiguration configFile = ConfigManager.getInstance().getConfig("config.yml");
    Webhook wh = new Webhook(ConfigManager.getInstance().getStringRaw(configFile, "WebhookURL"));

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        wh.setUsername(event.getPlayer().getName());
        wh.setAvatarUrl("https://minotar.net/helm/" + event.getPlayer().getName() + ".png");
        wh.setTts(false);
        wh.setContent(event.getMessage());
        try {
            wh.execute();
            event.isCancelled();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
