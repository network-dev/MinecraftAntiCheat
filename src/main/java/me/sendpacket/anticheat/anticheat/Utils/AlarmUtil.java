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

    //public static void Info(Player player, String msg)
    //{
     //   player.sendMessage("§7[§cAC§7] §7" + msg);
    //}
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
                    if(PlayerFlagTimer.get(p) != null)
                    {
                        if(PlayerFlagTimer.get(p) > 0)
                        {
                            PlayerFlagTimer.put(p,PlayerFlagTimer.get(p) - 1);
                        }
                    }
                    if(AvoidFlaggingPlayer.get(p) != null)
                    {
                        if(AvoidFlaggingPlayer.get(p) > 0)
                        {
                            AvoidFlaggingPlayer.put(p,AvoidFlaggingPlayer.get(p) - 1);
                        }
                    }
                }
            }
        }, 0, 1L);
    }



    public static void AddViolation(Player player, Check check, String details) {
        if (AvoidFlaggingPlayer.get(player) != null) {
            if (AvoidFlaggingPlayer.get(player) > 0) {
                return;
            }
        }

        check.AddViolationLevel(player);
        if(CheckManager.Enable_Messages) {
            player.sendMessage("§7[§cAC§7] §7" + check.name + " [" + check.ViolationLevel.get(player) + "]" + (details.length() > 0 ? " -> " + details : ""));
        }
        if(CheckManager.Enable_Kicks) {
            if(check.getViolationLevel(player) >= check.kick_max)
            {
                check.ResetViolationLevel(player);
                player.kickPlayer("§7[§cAC§7] \n" + "§7You were kicked for using cheats. [" + check.name+"]");
            }
        }
        if (!CheckManager.Enable_SetBacks) {
            return;
        }

        if (PlayerFlagTimer.get(player) != null) {
            if (PlayerFlagTimer.get(player) > 0) {
                return;
            }
        }

        PlayerFlagTimer.put(player, 5);
    }

    public static void AddViolation(Player player, Check check)
    {
        if (AvoidFlaggingPlayer.get(player) != null) {
            if (AvoidFlaggingPlayer.get(player) > 0) {
                return;
            }
        }

        check.AddViolationLevel(player);
        if(CheckManager.Enable_Messages) {
            player.sendMessage("§7[§cAC§7] §7" + check.name + " [" + check.ViolationLevel.get(player) + "]");
        }
        if(CheckManager.Enable_Kicks) {
            if(check.getViolationLevel(player) >= check.kick_max)
            {
                check.ResetViolationLevel(player);
                player.kickPlayer("§7[§cAC§7] \n" + "§7You were kicked for using cheats. [" + check.name+"]");
            }
        }

        if (!CheckManager.Enable_SetBacks) {
            return;
        }

        if(PlayerFlagTimer.get(player) != null)
        {
            if(PlayerFlagTimer.get(player) > 0)
            {
                return;
            }
        }

        PlayerFlagTimer.put(player, 5);
    }
}
