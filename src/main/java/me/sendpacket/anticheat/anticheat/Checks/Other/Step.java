package me.sendpacket.anticheat.anticheat.Checks.Other;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.GeneralManager;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;

class StepPlayer
{
    StepPlayer(Player p)
    {
        this.p = p;
        this.Logging = false;
        this.Times0 = 0;
    }
    public Player p;
    public ArrayList<Double> ydiff_list = new ArrayList<Double>();
    public Boolean Logging;
    public Integer Times0;
}


public class Step extends Check implements Listener {
    public Step() {
        super("Step", "Checks if the player has not jumped to get on a higher block.", CheckCategory.MOVEMENT, 10);
    }

    public static ArrayList<StepPlayer> PlayerList = new ArrayList<>();
    public HashMap<Player, Integer> FlagCount = new HashMap<>();
    public HashMap<Player, Integer> StepReset = new HashMap<>();

    public StepPlayer getStepPlayer(Player p)
    {
        for(StepPlayer sp : PlayerList)
        {
            if(sp.p.getUniqueId().equals(p.getUniqueId()))
            {
                return sp;
            }
        }

        StepPlayer new_step_player = new StepPlayer(p);
        PlayerList.add(new_step_player);

        return new_step_player;
    }
    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if(!player.getAllowFlight()) {
            double ydiff = event.getTo().getY() - event.getFrom().getY();

            if (PlayerUtil.HalfBlockUnder(player) || PlayerUtil.IsClimbingVineOrLadder(player) || player.getLocation().getBlock().getType() == Material.SLIME_BLOCK) {
                getStepPlayer(player).Logging = false;
                getStepPlayer(player).ydiff_list.clear();
                getStepPlayer(player).Times0 = 0;
                FlagCount.put(player, 0);
                StepReset.put(player, 0);
            }

            StepReset.put(player, StepReset.get(player) != null ? StepReset.get(player) + 1 : 1);
            if (StepReset.get(player) > 50) {
                FlagCount.put(player, 0);
                StepReset.put(player, 0);
            }

            if (ydiff >= 0.5f) { // This was added to prevent false positives with blocks like soulsand
                if (ydiff != 0.f && !getStepPlayer(player).Logging) {
                    getStepPlayer(player).Logging = true;
                } else {
                    if (getStepPlayer(player).Logging) {
                        if (ydiff == 0.f) {
                            getStepPlayer(player).Times0 += 1;
                            if (getStepPlayer(player).Times0 > 1) {
                                boolean only_positive = true;
                                for (Double dif : getStepPlayer(player).ydiff_list) {
                                    if (dif < 0) {
                                        only_positive = false;
                                    }
                                }
                                if (only_positive) {
                                    FlagCount.put(player, FlagCount.get(player) != null ? FlagCount.get(player) + 1 : 1);
                                    if (FlagCount.get(player) > 1) {
                                        StepReset.put(player, 0);
                                        AlarmUtil.AddViolation(player, CheckManager.getCheck("Step"));
                                    }
                                }
                                getStepPlayer(player).Logging = false;
                                getStepPlayer(player).ydiff_list.clear();
                                getStepPlayer(player).Times0 = 0;
                            }
                        } else {
                            getStepPlayer(player).ydiff_list.add(ydiff);
                        }
                    }
                }
            }
        }
    }
}
