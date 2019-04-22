package me.sendpacket.anticheat.anticheat.Checks.Other;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class Phase extends Check implements Listener {
    public Phase() {
        super("Phase", "Checks if the player is going throught a block.", CheckCategory.MOVEMENT, 20);
    }

    public static HashMap<Player, Location> FlyLastLocPlayers = new HashMap<>();
    public static HashMap<Player, Integer> FlyPlayers = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        double diff = event.getTo().distance(event.getFrom());
        // player.sendMessage("diff: "+diff);
    }
}