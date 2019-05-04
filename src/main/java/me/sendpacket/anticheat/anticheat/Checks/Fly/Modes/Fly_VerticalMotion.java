package me.sendpacket.anticheat.anticheat.Checks.Fly.Modes;

import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class Fly_VerticalMotion extends SubCheck {
    public Fly_VerticalMotion() {
        super("Fly VerticalMotion", CheckManager.Fly_Check);
    }

    public static HashMap<Player, Integer> FlyPlayers = new HashMap<>();
    public static HashMap<Player, Double> FlyPlayerLastYVelocity = new HashMap<>();
    public static HashMap<Player, Integer> FlyPlayerGroundTimer = new HashMap<>();
    public static HashMap<Player, Integer> WaitTimer = new HashMap<>();

    private boolean isSlimeBlockUnderPlayer(Player p)
    {
        for(int i = 0; i < 256; i ++)
        {
            if(p.getLocation().add(0,-i, 0).getBlock().getType() == Material.SLIME_BLOCK)
            {
                return true;
            }
        }
        return false;
    }
    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if(player.getAllowFlight())
            return;

        if(WaitTimer.get(player) != null)
        {
            if(WaitTimer.get(player) > 0)
            {
                WaitTimer.put(player, WaitTimer.get(player) - 1);
                return;
            }
        }

        if(player.isOnGround())
        {
            FlyPlayerGroundTimer.put(player, 0);
        }else{
            if(FlyPlayerGroundTimer.get(player) != null) {
                if (FlyPlayerGroundTimer.get(player) < 10) {
                    FlyPlayerGroundTimer.put(player, FlyPlayerGroundTimer.get(player) + 1);
                }
            }else{
                FlyPlayerGroundTimer.put(player, 1);
            }
        }

        if(PlayerUtil.IsInAir(player))
        {
            if(isSlimeBlockUnderPlayer(player)) {
                if (FlyPlayerLastYVelocity.get(player) != null) {
                    if (Math.abs(player.getVelocity().getY()) > Math.abs(FlyPlayerLastYVelocity.get(player)) && player.getFallDistance() == 0.0f) {
                        FlyPlayers.put(player, (FlyPlayers.get(player) != null) ? (FlyPlayers.get(player) + 1) : 1);
                        if(FlyPlayers.get(player) > 4) {
                            AlarmUtil.AddViolation(event.getPlayer(), CheckManager.Fly_Check, "Fly VerticalMotion.");
                        }
                    }else{
                        FlyPlayers.put(player, 0);
                    }
                }
                FlyPlayerLastYVelocity.put(player, player.getVelocity().getY());
            }else {
                double y_diff = MathUtil.GetDistanceBetween2Numbers(event.getTo().getY(),event.getFrom().getY());
                if(event.getTo().getY() > event.getFrom().getY()) {
                    if (y_diff > 0.D && FlyPlayerGroundTimer.get(player) == 10) {
                        FlyPlayers.put(player, (FlyPlayers.get(player) != null) ? (FlyPlayers.get(player) + 1) : 1);
                        if (FlyPlayers.get(player) > 3) {
                            AlarmUtil.AddViolation(event.getPlayer(), CheckManager.Fly_Check, "Fly VerticalMotion.");
                        }
                    } else {
                        FlyPlayers.put(player, 0);
                    }
                }else {
                    FlyPlayers.put(player, 0);
                }
            }
        }else{
            FlyPlayers.put(player, 0);
        }
    }
    public void onBlockPlace(BlockPlaceEvent event)
    {
        WaitTimer.put(event.getPlayer(), 5);
    }
}