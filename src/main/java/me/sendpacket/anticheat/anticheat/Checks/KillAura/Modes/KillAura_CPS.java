package me.sendpacket.anticheat.anticheat.Checks.KillAura.Modes;

import me.sendpacket.anticheat.anticheat.Managers.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class KillAura_CPS extends SubCheck {
    public KillAura_CPS() {
        super("KillAura CPS", CheckManager.KillAura_Check);
    }

    public static HashMap<Player, Integer> CpsFlagPlayers = new HashMap<>();
    public static HashMap<Player, Float> LastCpsPlayers = new HashMap<>();
    public static HashMap<Player, Float> CpsPlayers = new HashMap<>();
    public static HashMap<Player, Integer> CpsTimer = new HashMap<>();
    public static HashMap<Player, Boolean> CpsStart = new HashMap<>();
    public static HashMap<Player, Float> Cps = new HashMap<>();

    public void onTickUpdate() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (CpsStart.get(p) != null) {
                if (CpsStart.get(p) == true) {
                    CpsTimer.put(p, CpsTimer.get(p) != null ? CpsTimer.get(p) + 1 : 1);
                    if (CpsTimer.get(p) >= 20) {

                        Cps.put(p, CpsPlayers.get(p));

                        if (LastCpsPlayers.get(p) != null) {
                            double diff = MathUtil.GetDistanceBetween2Numbers(LastCpsPlayers.get(p), CpsPlayers.get(p));
                            if (diff < 2 && CpsPlayers.get(p) > 9) {
                                CpsFlagPlayers.put(p, CpsFlagPlayers.get(p) != null ? CpsFlagPlayers.get(p) + 1 : 1);
                            } else {
                                CpsFlagPlayers.put(p, 0);
                            }

                            if (CpsFlagPlayers.get(p) > (7 - (CpsPlayers.get(p) / 8.f))) {
                                AlarmUtil.AddViolation(p, CheckManager.KillAura_Check,"KillAura CPS with a difference of " + diff + " and " + CpsPlayers.get(p) + " CPS.");
                            }
                        }
                        LastCpsPlayers.put(p, CpsPlayers.get(p));
                        CpsStart.put(p, false);
                        CpsTimer.put(p, 0);
                        CpsPlayers.put(p, 0.f);
                    }
                }
            }
        }
    }

    public void onInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            CpsStart.put(player, true);
            CpsPlayers.put(player, CpsPlayers.get(player) != null ? CpsPlayers.get(player) + 1 : 1);
        }
    }
}
