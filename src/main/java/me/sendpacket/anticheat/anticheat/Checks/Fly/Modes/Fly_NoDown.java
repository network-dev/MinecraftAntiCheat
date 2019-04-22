package me.sendpacket.anticheat.anticheat.Checks.Fly.Modes;

import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class Fly_NoDown extends SubCheck implements Listener {
    public Fly_NoDown() {
        super("Fly NoDown", CheckManager.Fly_Check);
    }

    public static HashMap<Player, Location> FlyLastLocPlayers = new HashMap<>();
    public static HashMap<Player, Integer> FlyPlayers = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if(player.getAllowFlight())
            return;

        if(PlayerUtil.IsInAir(player))
        {
            if (FlyLastLocPlayers.get(player) != null) {
                if (player.getLocation().getY() == FlyLastLocPlayers.get(player).getY()) {
                    FlyPlayers.put(player, (FlyPlayers.get(player) != null) ? (FlyPlayers.get(player) + 1) : 1);
                    if (FlyPlayers.get(player) > 4) {
                        AlarmUtil.AddViolation(event.getPlayer(), CheckManager.Fly_Check,"Fly NoDown.");
                    }
                }
            }
        }else{
            if(FlyPlayers.get(player) != null)
            {
                FlyPlayers.put(player, 0);
            }
        }

        FlyLastLocPlayers.put(player, player.getLocation());
    }
}
