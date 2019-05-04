package me.sendpacket.anticheat.anticheat.Checks.Speed.Modes;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class Speed_MiniHop extends SubCheck {
    public Speed_MiniHop() {
        super("Speed_MiniHop", CheckManager.Speed_Check);
    }

    public static HashMap<Player, Double> LastYDiff = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if (player.getAllowFlight())
            return;

        double ydiff = event.getTo().getY() - event.getFrom().getY();

        if(LastYDiff.get(player) != null)
        {
            if(PlayerUtil.IsSameBlockAround(player, Material.AIR, 2,0.5f) &&
                    PlayerUtil.IsSameBlockAround(player, Material.AIR, 0,0.5f) &&
                    player.getLocation().add(0, -1, 0).getBlock().getType() != Material.SLIME_BLOCK) {
                if (LastYDiff.get(player) <= 0.f && ydiff > 0.f && ydiff < 0.4 || ydiff != 0.f && ydiff == -(LastYDiff.get(player))) {
                    AlarmUtil.AddViolation(player, CheckManager.Speed_Check, "Speed MiniHop with y axis difference of " + ydiff + ".");
                }
            }
        }

        LastYDiff.put(player, ydiff);
    }
}
