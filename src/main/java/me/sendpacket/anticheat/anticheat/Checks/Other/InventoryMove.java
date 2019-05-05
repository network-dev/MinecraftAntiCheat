package me.sendpacket.anticheat.anticheat.Checks.Other;

import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Managers.CheckManager;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class InventoryMove extends Check {
    public InventoryMove() {
        super("InventoryMove", "Checks if the player is moving with the inventory open.", CheckCategory.MOVEMENT, 5);
    }

    public static HashMap<Player, Boolean> PlayerList = new HashMap();
    public static HashMap<Player, Boolean> Moving = new HashMap();
    public static HashMap<Player, Double> LastSpeed = new HashMap();
    public static HashMap<Player, Location> LastLocation = new HashMap();
    public static HashMap<Player, Integer> SlowerCount = new HashMap();

    public void onTickUpdate() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getAllowFlight())
                continue;

            if (player.getVelocity().getY() > 0.D)
                continue;

            if (LastLocation.get(player) != null) {
                double clean_dist = MathUtil.GetDistanceXZ(LastLocation.get(player).getX(), LastLocation.get(player).getZ(), player.getLocation().getX(), player.getLocation().getZ());

                if (clean_dist > 0.f) {
                    if (LastSpeed.get(player) != null) {
                        if (LastSpeed.get(player) < clean_dist && clean_dist != 0.D && LastSpeed.get(player) != 0.D) {
                            Moving.put(player, true);
                            SlowerCount.put(player, 0);
                        } else {
                            SlowerCount.put(player, SlowerCount.get(player) != null ? SlowerCount.get(player) + 1 : 1);
                            if (SlowerCount.get(player) > 2) {
                                Moving.put(player, false);
                            }
                        }
                    }
                } else {
                    Moving.put(player, false);
                }
                LastSpeed.put(player, clean_dist);
            }
            LastLocation.put(player, player.getLocation());
        }
    }

    public void onInventoryClick(InventoryClickEvent event)
    {
        if(!enabled)
            return;

        Player p = (Player)event.getWhoClicked();

        if(Moving.get(p) != null)
        {
            if(Moving.get(p) == true)
            {
                AlarmUtil.AddViolation(p, CheckManager.getCheck("InventoryMove"));
            }
        }
    }
}