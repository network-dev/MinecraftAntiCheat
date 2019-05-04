package me.sendpacket.anticheat.anticheat.Checks.Other;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class AimAssist extends Check {
    public AimAssist() {
        super("AimAssist", "Checks if the player is sending rotations that are too smooth.", CheckCategory.COMBAT, 10);
    }

    public static HashMap<Player, Integer> AimAssistCount = new HashMap<>();
    public static HashMap<Player, Integer> AimAssistTimer = new HashMap<>();
    public static HashMap<Player, Float> AimAssistLastYaw = new HashMap<>();
    public static HashMap<Player, Float> AimAssistLastPitch = new HashMap<>();
    public static HashMap<Player, Integer> AimAssistHitTimer = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        float yaw_diff = MathUtil.ClampYaw(event.getTo().getYaw() - event.getFrom().getYaw());
        float pitch_diff = event.getTo().getPitch() - event.getFrom().getPitch();

        if(AimAssistHitTimer.get(player) != null)
        {
            if(AimAssistHitTimer.get(player) > 0)
            {
                if(AimAssistLastPitch.get(player) != null && AimAssistLastYaw.get(player) != null)
                {
                    float yaw_diff_2 = MathUtil.ClampYaw(AimAssistLastYaw.get(player) - yaw_diff);
                    float pitch_diff_2 = AimAssistLastPitch.get(player) - pitch_diff;

                    if(Math.abs(AimAssistLastYaw.get(player)) > 1.f) {
                        if (Math.abs(yaw_diff_2) > 0.0 && Math.abs(yaw_diff_2) < 0.3 && Math.abs(pitch_diff_2) > 0.0 && Math.abs(pitch_diff_2) < 0.3) {
                            AimAssistCount.put(player, AimAssistCount.get(player) != null ? AimAssistCount.get(player) + 1 : 1);
                        }
                    }
                }

                AimAssistLastYaw.put(player, yaw_diff);
                AimAssistLastPitch.put(player, pitch_diff);
                AimAssistTimer.put(player, AimAssistTimer.get(player) != null ? AimAssistTimer.get(player) + 1 : 1);

                if(AimAssistTimer.get(player) > 20)
                {
                    if(AimAssistCount.get(player) != null) {
                        if(AimAssistCount.get(player) > 3)
                        {
                            AlarmUtil.AddViolation(player, CheckManager.getCheck("AimAssist"), "Smoothness: " + AimAssistCount.get(player));
                        }

                        AimAssistCount.put(player,0);
                    }
                    AimAssistTimer.put(player, 0);
                }

                AimAssistHitTimer.put(player, AimAssistHitTimer.get(player) - 1);
            }
        }
    }
    public void onEnityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() instanceof Player)
        {
            Player player = (Player)event.getDamager();
            AimAssistHitTimer.put(player, 10);
        }
    }
}