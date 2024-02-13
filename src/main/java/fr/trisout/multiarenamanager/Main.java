package fr.trisout.multiarenamanager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin implements Listener {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig(); // Crée le fichier config.yml s'il n'existe pas
        config = getConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            String joinCommand = config.getString("onJoinCommand", "bw join 1v1");
            player.performCommand(joinCommand);

            World world = player.getWorld();
            if (world.getName().equals(config.getString("kickWorld", "world"))) {
                String errorMessage = config.getString("errorMessage", "Une erreur s'est produite, veuillez réessayer");
                player.kickPlayer(errorMessage);
            }
        }, 1L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (world.getName().equals(config.getString("kickWorld", "world"))) {
                String changeWorldMessage = config.getString("changeWorldMessage", "Retour au lobby");
                player.kickPlayer(changeWorldMessage);
            }
        }, 1L);
    }
}
