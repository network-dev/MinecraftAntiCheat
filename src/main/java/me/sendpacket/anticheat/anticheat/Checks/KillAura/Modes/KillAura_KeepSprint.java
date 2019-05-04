package me.sendpacket.anticheat.anticheat.Checks.KillAura.Modes;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

class PlayerSpeed
{
    ArrayList<Double> SpeeList = new ArrayList<>();
}

public class KillAura_KeepSprint  extends SubCheck {
    public KillAura_KeepSprint() {
        super("KillAura KeepSprint", CheckManager.KillAura_Check);
    }

    public static HashMap<Player, Integer> HitTimer = new HashMap<>();;
    public static HashMap<Player, PlayerSpeed> Speeds = new HashMap<>();;
    public static HashMap<Player, Boolean> WasSprinting = new HashMap<>();;

    public void onTickUpdate() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (HitTimer.get(p) != null) {
                if (HitTimer.get(p) > 0) {
                    HitTimer.put(p, HitTimer.get(p) - 1);
                }
            }
        }
    }

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        double speed = event.getFrom().distance(event.getTo());

        if(HitTimer.get(player) != null) {
            if (HitTimer.get(player) == 0.f) {
                WasSprinting.put(player,speed > 0.23);
            }
        }

        if(HitTimer.get(player) != null) {
            if (HitTimer.get(player) > 0) {
                if (WasSprinting.get(player) != null) {
                    if(WasSprinting.get(player)) {
                        if (Speeds.get(player) == null) {
                            PlayerSpeed ps = new PlayerSpeed();
                            ps.SpeeList.add(speed);
                            Speeds.put(player, ps);
                        } else {
                            PlayerSpeed ps = Speeds.get(player);
                            ps.SpeeList.add(speed);
                            Speeds.put(player, ps);
                        }
                    }
                }
            }

            if (HitTimer.get(player) <= 1 && HitTimer.get(player) > 0) {
                if (Speeds.get(player) != null) {
                    if(Speeds.get(player).SpeeList.size() > 1) {
                        boolean not_changing = true;
                        double last_speed = -99.0;
                        double diff = 0.0D;
                        for (Double d : Speeds.get(player).SpeeList) {
                            if (last_speed != -99.0) {
                                diff = MathUtil.GetDistanceBetween2Numbers(last_speed, d);
                                if (diff > 0.02) {
                                    not_changing = false;
                                }
                            }
                            last_speed = d;
                        }
                        if (not_changing && last_speed != -99.0D) {
                            AlarmUtil.AddViolation(player, CheckManager.KillAura_Check,"KillAura KeepSprint with a speed change of " + diff + ".");
                        }
                    }
                    PlayerSpeed ps = new PlayerSpeed();
                    Speeds.put(player, ps);
                }
            }
        }
    }

    public void onEnityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();

            if(!player.isOnGround())
                return;

            ((LivingEntity) event.getEntity()).setHealth(20);
            if (HitTimer.get(player) == null) {
                HitTimer.put(player, 10);
            }else{
                if(HitTimer.get(player) == 0.f)
                {
                    HitTimer.put(player, 10);
                }
            }
        }
    }
}
