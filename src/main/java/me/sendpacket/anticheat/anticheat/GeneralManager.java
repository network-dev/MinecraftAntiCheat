package me.sendpacket.anticheat.anticheat;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class GeneralManager implements Listener {

    public static HashMap<Player, Location> LastGroundPosition = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(player.isOnGround())
        {
            LastGroundPosition.put(player, player.getLocation());
        }
    }
}
