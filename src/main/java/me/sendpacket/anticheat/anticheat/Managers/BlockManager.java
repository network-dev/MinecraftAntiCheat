package me.sendpacket.anticheat.anticheat.Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class BlockManager {

    public static HashMap<Player, Integer> PlayerMovementBlockLog = new HashMap<>();
    public static HashMap<Player, Integer> PlayerCombatBlockLog = new HashMap<>();

    public static int getMovementBlockTimer(Player player)
    {
        if(PlayerMovementBlockLog.get(player) != null)
        {
            return PlayerMovementBlockLog.get(player);
        }
        return 0;
    }

    public static int getCombatBlockTimer(Player player)
    {
        if(PlayerCombatBlockLog.get(player) != null)
        {
            return PlayerCombatBlockLog.get(player);
        }
        return 0;
    }

    public static void updateBlockTimers(Player player)
    {
        if(PlayerMovementBlockLog.get(player) != null)
        {
            PlayerMovementBlockLog.put(player, PlayerMovementBlockLog.get(player) > 0 ? PlayerMovementBlockLog.get(player) - 1 : 0);
        }
        if(PlayerCombatBlockLog.get(player) != null)
        {
            PlayerCombatBlockLog.put(player, PlayerCombatBlockLog.get(player) > 0 ? PlayerCombatBlockLog.get(player) - 1 : 0);
        }
    }

    public static boolean isTimerOverZero(Player player)
    {
        if(getMovementBlockTimer(player) > 0 || getCombatBlockTimer(player) > 0)
        {
            return true;
        }
        return false;
    }

    public static boolean onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        int BlockTimer = getMovementBlockTimer(player);
        if(BlockTimer > 5)
        {
            return true;
        }
        return event.isCancelled();
    }

    public static boolean onEntityDamage(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() instanceof Player && event.getDamager() != event.getEntity())
        {
            Player player = (Player)event.getDamager();
            int BlockTimer = getCombatBlockTimer(player);
            if(BlockTimer > 5)
            {
                return true;
            }
        }
        return event.isCancelled();
    }

    public static void onTickUpdate() {
        for(Player p : Bukkit.getServer().getOnlinePlayers())
        {
            if(isTimerOverZero(p))
            {
                updateBlockTimers(p);
            }
        }
    }
}
