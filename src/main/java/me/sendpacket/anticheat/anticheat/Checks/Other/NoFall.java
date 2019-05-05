package me.sendpacket.anticheat.anticheat.Checks.Other;

import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Managers.CheckManager;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoFall extends Check {
    public NoFall() {
        super("NoFall", "Checks if the player is sending onground packets while being in the air.", CheckCategory.MOVEMENT, 20);
    }

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if (player.getAllowFlight() || player.getLocation().add(0,-1,0).getBlock().getType() == Material.SLIME_BLOCK)
            return;

        if(player.isOnGround() && PlayerUtil.DistanceToGround(player) > 1.f )
        {
            AlarmUtil.AddViolation(player, CheckManager.getCheck("NoFall"), "Distance To Ground: " + PlayerUtil.DistanceToGround(player));
        }
    }
}
