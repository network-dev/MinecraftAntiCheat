package me.sendpacket.anticheat.anticheat.Checks.Other;

import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class Scaffold extends Check {
    public Scaffold() {
        super("Scaffold", "Checks if the player is building bridges faster than normal.", CheckCategory.MOVEMENT, 10);
    }

    public static HashMap<Player, Float> LastPitch = new HashMap<>();
    public static HashMap<Player, Double> LastSpeed = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        double clean_dist = MathUtil.GetDistanceXZ(event.getFrom().getX(), event.getFrom().getZ(), event.getTo().getX(), event.getTo().getZ());

        LastSpeed.put(event.getPlayer(), clean_dist);
        LastPitch.put(event.getPlayer(), event.getPlayer().getLocation().getPitch());
    }
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (LastPitch.get(event.getPlayer()) != null && LastSpeed.get(event.getPlayer()) != null) {
            double diff = MathUtil.GetDistanceBetween2Numbers(event.getPlayer().getLocation().getPitch(), LastPitch.get(event.getPlayer()));
            if (diff > 40 || LastSpeed.get(event.getPlayer()) > 0.20)
            {
                Block placed_b = event.getBlockPlaced();
                if(placed_b != null)
                {
                    Location loc_front = PlayerUtil.getLocationFront(event.getPlayer(), 0).add(0,-1,0);
                    Block predicted_block = event.getPlayer().getWorld().getBlockAt(loc_front);
                    if(predicted_block != null) {
                        if (placed_b.getLocation().equals(predicted_block.getLocation()) && placed_b.getLocation().add(0,-1,0).getBlock().getType() == Material.AIR) {
                            AlarmUtil.AddViolation(event.getPlayer(), this, "Diff: " + diff + " Speed: " + LastSpeed.get(event.getPlayer()));
                        }
                    }
                }
            }
        }
    }
}