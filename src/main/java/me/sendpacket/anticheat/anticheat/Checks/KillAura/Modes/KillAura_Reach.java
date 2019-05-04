package me.sendpacket.anticheat.anticheat.Checks.KillAura.Modes;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class KillAura_Reach extends SubCheck {
    public KillAura_Reach() {
        super("KillAura Reach", CheckManager.KillAura_Check);
    }

    public void onEnityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity)
        {
            Player player = (Player)event.getDamager();
            LivingEntity entity = (LivingEntity)event.getEntity();

            double dist = player.getLocation().distance(entity.getLocation());

            Location temp_loc_player = player.getLocation();
            Location temp_loc_entity = entity.getLocation();

            temp_loc_player.setY(0);
            temp_loc_entity.setY(0);

            double real_dist = temp_loc_player.distance(temp_loc_entity);
            int ping = PlayerUtil.getPing(player);
            double max_dist = 3.69D + (((double)ping / 100.D) * 0.5D);
            if(real_dist >= max_dist)
            {
                AlarmUtil.AddViolation(player, CheckManager.KillAura_Check,"KillAura Reach with distance of " + real_dist + " and a ping of " + ping + ".");
            }
        }
    }
}
