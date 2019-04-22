package me.sendpacket.anticheat.anticheat.Checks.Other;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class Jesus extends Check implements Listener {
    public Jesus() {
        super("Jesus", "Checks if the player is floating above water or lava.", CheckCategory.MOVEMENT, 10);
    }

    public static HashMap<Player, Integer> PlayerList = new HashMap();
    public static HashMap<Player, Double> LastYDiff = new HashMap();
    public static HashMap<Player, Double> LastLastYDiff = new HashMap();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if(player.getAllowFlight())
            return;

        double ydiff = event.getTo().getY() - event.getFrom().getY();

        boolean in_liquid = false;
        if(PlayerUtil.IsLiquidAround(player, -1, 0.5f) || PlayerUtil.IsLiquidAround(player, 0, 0.5f)) {
            if (player.getLocation().add(0,-0.5f,0).getBlock().isLiquid()) {
                in_liquid = true;
            }
        }

        if(in_liquid) {
            if (LastYDiff.get(player) != null && LastLastYDiff.get(player) != null) {
                double diff = MathUtil.GetDistanceBetween2Numbers(LastLastYDiff.get(player),Math.abs(ydiff));
                if (diff == 0.0f) {
                    AlarmUtil.AddViolation(player, CheckManager.getCheck("Jesus"));
                }
            }
        }

        if(LastYDiff.get(player) != null) {
            LastLastYDiff.put(player, LastYDiff.get(player));
        }
        LastYDiff.put(player, ydiff);
    }
}

