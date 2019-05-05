package me.sendpacket.anticheat.anticheat.Utils;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class AlarmUtil implements Listener {

    public static HashMap<Player, Integer> AvoidFlaggingPlayer = new HashMap<>();
    public static HashMap<Player, Integer> PlayerFlagTimer = new HashMap<>();
    public static HashMap<Player, Boolean> PlayerShouldDebug = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        if(event.getEntity() instanceof Player)
        {
            Player player = (Player)event.getEntity();
            AvoidFlaggingPlayer.put(player, 20);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event)
    {
        Player player = event.getPlayer();
        AvoidFlaggingPlayer.put(player, 20);
    }

    public static void UpdateAvoidTimer()
    {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(AntiCheat.Plugin, new Runnable() {
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if(getFlagTimer(p) > 0)
                    {
                        PlayerFlagTimer.put(p,PlayerFlagTimer.get(p) - 1);
                    }
                    if(getAvoidTimer(p) > 0)
                    {
                        AvoidFlaggingPlayer.put(p,AvoidFlaggingPlayer.get(p) - 1);
                    }
                }
            }
        }, 0, 1L);
    }

    public static int getAvoidTimer(Player player)
    {
        if (AvoidFlaggingPlayer.get(player) != null)
        {
            return AvoidFlaggingPlayer.get(player);
        }
        return 0;
    }

    public static int getFlagTimer(Player player)
    {
        if (PlayerFlagTimer.get(player) != null)
        {
            return PlayerFlagTimer.get(player);
        }
        return 0;
    }

    public static void AddViolation(Player player, Check check, int addition_level, String details) {
        if (getAvoidTimer(player) > 0)
            return;

        check.AddViolationLevel(player, addition_level);

        if(CheckManager.Enable_Messages)
        {
            String message = ("§7[§cAC§7] §7" + check.name + " [" + check.ViolationLevel.get(player) + "]");
            if(details.length() > 0)
            {
                message += (details.length() > 0 ? " -> " + details : "");
            }
            player.sendMessage(message);
        }

        if(CheckManager.Enable_Kicks)
        {
            if(check.getViolationLevel(player) >= check.kick_max)
            {
                check.ResetViolationLevel(player);
                player.kickPlayer("§7[§cAC§7] \n" + "§7You were kicked for using cheats. [" + check.name+"]");
            }
        }

        if (getFlagTimer(player) > 0 || !CheckManager.Enable_SetBacks)
            return;

        PlayerFlagTimer.put(player, 5);
    }
    public static void AddViolation(Player player, Check check, int addition_level)
    {
        AddViolation(player, check, addition_level, "");
    }
    public static void AddViolation(Player player, Check check)
    {
        AddViolation(player, check, 1, "");
    }
    public static void AddViolation(Player player, Check check,  String details)
    {
        AddViolation(player, check, 1, details);
    }
}
