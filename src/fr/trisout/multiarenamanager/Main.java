package fr.trisout.multiarenamanager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(this, () -> player.performCommand("bw join 1v1"), 1L);
        Bukkit.getScheduler().runTaskLater(this, () -> {
            World world = player.getWorld();
            if (world.getName().equals("world")) {
                player.kickPlayer("Une erreur s'est produite, veuillez réessayer");
            }
        }, 20L);
    }
    @EventHandler
    public void OnPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

    @EventHandler
    public void onPlayerGamemodeChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (player.getGameMode() == GameMode.CREATIVE)
                player.performCommand("bw leave");
                player.sendMessage("§a§lMODERATION MODE");
                player.performCommand("bw gui");
        }, 1L);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (player.getGameMode() != GameMode.CREATIVE)
             if (world.getName().equals("world")) {
                 player.kickPlayer("Retour au lobby");
            }
        }, 1L);
    }
}

