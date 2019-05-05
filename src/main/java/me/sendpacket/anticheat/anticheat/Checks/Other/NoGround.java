package me.sendpacket.anticheat.anticheat.Checks.Other;

import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Managers.CheckManager;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class NoGround extends Check {
    public NoGround() {
        super("NoGround", "Checks if the player is sending air packets while being on the ground.", CheckCategory.MOVEMENT, 20);
    }

    public static HashMap<Player, Double> LastYDiff = new HashMap<>();
    public static HashMap<Player, Double> LastYVel = new HashMap<>();
    public static HashMap<Player, Integer> EqualTimer = new HashMap<>();
    public static HashMap<Player, Float> LastFallDist = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if (player.getAllowFlight() || player.getLocation().add(0,-1,0).getBlock().getType() == Material.SLIME_BLOCK)
            return;

        double ydiff = event.getTo().getY() - event.getFrom().getY();

        if(LastYDiff.get(player) != null && LastYVel.get(player) != null && LastFallDist.get(player) != null) {
            if (!player.isOnGround() && player.getFallDistance() == LastFallDist.get(player)) {
                if (LastYDiff.get(player) == 0.f && ydiff == 0.f) {
                    if (LastYVel.get(player) == player.getVelocity().getY() && LastYVel.get(player) != 0.0) {
                        EqualTimer.put(player, EqualTimer.get(player) != null ? EqualTimer.get(player) + 1 : 1);
                        if (EqualTimer.get(player) > 2) {
                            AlarmUtil.AddViolation(player, CheckManager.getCheck("NoGround"), "Match: " + EqualTimer.get(player));
                        }
                    }else{
                        EqualTimer.put(player, 0);
                    }
                }else{
                    EqualTimer.put(player, 0);
                }
            }else{
                EqualTimer.put(player, 0);
            }
        }

        LastFallDist.put(player, player.getFallDistance());
        LastYDiff.put(player, ydiff);
        LastYVel.put(player, player.getVelocity().getY());
    }
}


