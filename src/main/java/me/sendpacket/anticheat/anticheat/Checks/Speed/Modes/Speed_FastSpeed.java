package me.sendpacket.anticheat.anticheat.Checks.Speed.Modes;

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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Speed_FastSpeed extends SubCheck {
    public Speed_FastSpeed() {
        super("Speed_FastSpeed", CheckManager.Speed_Check);
    }

    public static HashMap<Player, Integer> PlayerDamageList = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if (player.getAllowFlight())
            return;

        if(AlarmUtil.PlayerShouldDebug.get(player) != null)
        {
            if(AlarmUtil.PlayerShouldDebug.get(player) == true)
            {
                player.sendMessage("§7[§cAC-Debug§7] §7" + "fd: " + player.getFallDistance() + " g: " + player.isOnGround() + " v: " + player.getVelocity().getX() + "|" + player.getVelocity().getY() + "|"+player.getVelocity().getZ() + " dg: " + PlayerUtil.DistanceToGround(player) + " mat: " + player.getLocation().getBlock().getType() + " mat2: " + player.getLocation().add(0,-1, 0).getBlock().getType() + " mat3: " + PlayerUtil.IsSameBlockAround(player, player.getLocation().getBlock().getType(), 0, 1.f) + " mat4: " + PlayerUtil.IsSameBlockAround(player, player.getLocation().getBlock().getType(), -1, 1.f));
            }
        }

        Location temp_loc_player = new Location(player.getWorld(),event.getFrom().getX(),0, event.getFrom().getZ());
        Location temp_loc_entity = new Location(player.getWorld(),event.getTo().getX(),0, event.getTo().getZ());

        double clean_dist = temp_loc_player.distance(temp_loc_entity);

        boolean took_damage = false;
        double max_walk_speed = (double)((player.getWalkSpeed() / 0.2f) * 0.65f);

        if(PlayerDamageList.get(player) != null)
        {
            if(PlayerDamageList.get(player) > 0)
            {
                took_damage = true;
                PlayerDamageList.put(player, PlayerDamageList.get(player) - 1);
            }
        }

        for(PotionEffect po : player.getActivePotionEffects())
        {
            if (po.getType().equals(PotionEffectType.SPEED))
            {
                max_walk_speed += ( (po.getAmplifier() + 1) * 0.425);
            }
        }

        if(player.isOnGround() && !player.getAllowFlight() && clean_dist > (max_walk_speed) || !player.isOnGround() && !player.getAllowFlight() && clean_dist >= 1.0 )
        {
            if(took_damage)
            {
                return;
            }
            AlarmUtil.AddViolation(player, CheckManager.Speed_Check, "Speed " + MathUtil.GetDistanceBetween2Numbers(clean_dist, max_walk_speed) + " faster than normal");
        }
        //walkspeed:0.2 walk ground 1.1 | 1.0295
        //
    }
    public void onEnityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player p = (Player)event.getDamager();
            PlayerDamageList.put(p, 20);
        }
    }
    public void onEntityDamageEvent(EntityDamageEvent event)
    {
        if(event.getEntity() instanceof Player)
        {
            Player p = (Player)event.getEntity();
            PlayerDamageList.put(p, 20);
        }
    }
}
