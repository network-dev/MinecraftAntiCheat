package me.sendpacket.anticheat.anticheat.Checks.Fly.Modes;

import me.sendpacket.anticheat.anticheat.Managers.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class Fly_FallCycle extends SubCheck {
    public Fly_FallCycle() {
        super("Fly FallCycle", CheckManager.Fly_Check);
    }

    public static HashMap<Player, Integer> PlayerCount = new HashMap<>();
    public static HashMap<Player, Double> PlayerLastDist = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if (player.getAllowFlight()) // If the player has the ability to fly, we can't check.
            return;

        if(PlayerUtil.IsInAir(player) && !player.getAllowFlight())
        {
            double dist = event.getFrom().distance(event.getTo());

            if(PlayerLastDist.get(player) != null) {
                if (PlayerLastDist.get(player) > dist && event.getPlayer().getFallDistance() > 0.f) { // If the player is going up while falling
                    PlayerCount.put(player, PlayerCount.get(player) != null ? PlayerCount.get(player) + 1 : 1);
                    if (PlayerCount.get(player) > 1) {
                        AlarmUtil.AddViolation(event.getPlayer(), CheckManager.Fly_Check,"Fly FallCycle " + PlayerCount.get(player) + " repeats.");
                    }
                }
            }

            PlayerLastDist.put(player, dist);
        }else{
            PlayerCount.put(player, 0);
            PlayerLastDist.put(player, null);
        }
    }
}
