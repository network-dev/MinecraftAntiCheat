package me.sendpacket.anticheat.anticheat.Checks.KillAura.Modes;

import me.sendpacket.anticheat.anticheat.Managers.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class KillAura_WallHit extends SubCheck {
    public KillAura_WallHit() {
        super("KillAura WallHit", CheckManager.KillAura_Check);
    }

    public void onEnityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity)
        {
            Player player = (Player) event.getDamager();
            LivingEntity entity = (LivingEntity) event.getEntity();

            if(!player.hasLineOfSight(entity)) {
                Block targetblock = player.getTargetBlock(null, (int) entity.getLocation().distance(player.getLocation()));
                Block targetblock2 = player.getWorld().getBlockAt(targetblock.getLocation().add(0, -0.25,0));
                Block targetblock3 = player.getWorld().getBlockAt(targetblock.getLocation().add(0, 0.25,0));

                if (!PlayerUtil.isHalfBlock(targetblock) && targetblock.getType() != Material.AIR && targetblock2.getType() != Material.AIR && targetblock3.getType() != Material.AIR) {
                    AlarmUtil.AddViolation(player, CheckManager.KillAura_Check,"KillAura WallHit.");
                }
            }
        }
    }
}