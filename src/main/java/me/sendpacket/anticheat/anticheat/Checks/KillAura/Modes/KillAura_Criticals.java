package me.sendpacket.anticheat.anticheat.Checks.KillAura.Modes;

import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class KillAura_Criticals extends SubCheck {
    public KillAura_Criticals() {
        super("KillAura Criticals", CheckManager.KillAura_Check);
    }

    public static HashMap<Player, Double> LastYDiff = new HashMap<>();
    public static HashMap<Player, Double> LastYVel = new HashMap<>();
    public static HashMap<Player, Float> LastFallDist = new HashMap<>();
    public static HashMap<Player, Integer> EqualVelocity = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = (Player) event.getPlayer();
        double ydiff = event.getTo().getY() - event.getFrom().getY();

        if(LastYDiff.get(player) != null && LastYVel.get(player) != null && LastFallDist.get(player) != null)
        {
            if(player.getVelocity().getY() == LastYVel.get(player))
            {
                EqualVelocity.put(player, EqualVelocity.get(player) != null ? EqualVelocity.get(player) + 1 : 1);
                if(EqualVelocity.get(player) > 2) {
                    if (LastYDiff.get(player) > 0 && ydiff < 0 && PlayerUtil.DistanceToGround(player) < 1) {
                        if (ydiff == -(LastYDiff.get(player))) {
                            AlarmUtil.AddViolation(player, CheckManager.KillAura_Check,"KillAura Criticals.");
                        }
                    }
                    EqualVelocity.put(player, 0);
                }
            }else{
                EqualVelocity.put(player, 0);
            }
        }

        LastFallDist.put(player, player.getFallDistance());
        LastYDiff.put(player, ydiff);
        LastYVel.put(player, player.getVelocity().getY());
    }
}