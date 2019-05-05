package me.sendpacket.anticheat.anticheat.Checks.Fly.Modes;

import me.sendpacket.anticheat.anticheat.Managers.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class Fly_Gravity extends SubCheck {
    public Fly_Gravity() { super("Fly Gravity", CheckManager.Fly_Check); }

    public static HashMap<Player, Double> PlayerList = new HashMap<>();
    public static HashMap<Player, Integer> FlyPlayers = new HashMap<>();
    public static HashMap<Player, Material> LastBlockStanding = new HashMap<>();

    private boolean canBeFlagged(Player p)
    {
        if(LastBlockStanding.get(p) != null) {
            if (LastBlockStanding.get(p) == Material.SLIME_BLOCK) {
                return false;
            }
        }
        if(p.getLocation().add(0,1,0).getBlock().getType() != Material.AIR)
        {
            return false;
        }
        if(PlayerUtil.DistanceToGround(p) < 1)
        {
            return false;
        }

        return true;
    }
    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if (player.getAllowFlight())
            return;

        if(player.isOnGround())
        {
            LastBlockStanding.put(player, player.getLocation().add(0,-1,0).getBlock().getType());
        }

        double ydiff = event.getTo().getY() - event.getFrom().getY();

        if (PlayerList.get(player) != null) {
            double next_ydiff = (PlayerList.get(player) - 0.08D) * 0.9800000190734863D;
            if (PlayerUtil.IsInAir(player) && Math.abs(next_ydiff) >= 0.005 && PlayerUtil.IsSameBlockAround(player, Material.AIR, 2,0.5f)) {
                if (Math.abs(next_ydiff) - Math.abs(ydiff) >= 0.0001) {
                    if(canBeFlagged(player)) {
                        FlyPlayers.put(player, (FlyPlayers.get(player) != null) ? (FlyPlayers.get(player) + 1) : 1);
                        AlarmUtil.AddViolation(event.getPlayer(), CheckManager.Fly_Check,"Fly Gravity prediction difference of " + (Math.abs(next_ydiff) - Math.abs(ydiff)) + ".");
                    }
                } else {
                    FlyPlayers.put(player, 0);
                }
            } else {
                FlyPlayers.put(player, 0);
            }
        }

        PlayerList.put(player,ydiff);
    }
}
