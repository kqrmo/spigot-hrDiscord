package eu.hiveram.hrDiscord.Listeners;

import com.vexsoftware.votifier.model.VotifierEvent;
import com.vexsoftware.votifier.model.Vote;
import eu.hiveram.hrDiscord.ConfigManager;
import eu.hiveram.hrDiscord.Webhook;
import eu.hiveram.hrDiscord.hrDiscord;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.IOException;

public class VoteListener implements Listener {
    FileConfiguration configFile = ConfigManager.getInstance().getConfig("config.yml");
    Webhook wh = new Webhook(ConfigManager.getInstance().getStringRaw(configFile, "Votifier.VoteChannel"));

        @EventHandler(priority = EventPriority.NORMAL)
        public void onVotifierEvent (VotifierEvent event){
            if (ConfigManager.getInstance().getConfig("config.yml").getBoolean("Votifier.Enabled")) {

                Vote vote = event.getVote();
                String voteMessage = ConfigManager.getInstance().getStringRaw(configFile, "Votifier.VoteMessage")
                        .replace("{player}", vote.getUsername());
                wh.setUsername(ConfigManager.getInstance().getStringRaw(configFile, "Settings.Username"));
                wh.setAvatarUrl(ConfigManager.getInstance().getStringRaw(configFile, "Settings.Avatar"));
                wh.setTts(false);
                wh.addEmbed(new Webhook.EmbedObject()
                        .setAuthor(voteMessage, "", "https://minotar.net/helm/" + vote.getUsername() + ".png")
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
}
