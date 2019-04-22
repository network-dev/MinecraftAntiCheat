package me.sendpacket.anticheat.anticheat.Checks.Fly.Modes;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class Fly_Speed extends SubCheck implements Listener {
    public Fly_Speed() {
        super("Fly Speed", CheckManager.Fly_Check);
    }

    public static HashMap<Player, Double> FlyLastSpeed = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if (player.getAllowFlight())
            return;

        double dist = event.getFrom().distance(event.getTo());

        if(FlyLastSpeed.get(player) != null)
        {
            double diff = MathUtil.GetDistanceBetween2Numbers(FlyLastSpeed.get(player), dist);

            if(diff == 0.0 && dist > 0.10 && PlayerUtil.IsInAir(player))
            {
                AlarmUtil.AddViolation(event.getPlayer(), CheckManager.Fly_Check, "Fly Speed with speed acceleration of " + diff + ".");
            }
        }

        FlyLastSpeed.put(player, dist);
    }
}