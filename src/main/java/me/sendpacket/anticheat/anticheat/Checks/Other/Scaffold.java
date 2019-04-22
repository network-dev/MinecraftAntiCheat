package me.sendpacket.anticheat.anticheat.Checks.Other;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Scaffold extends Check implements Listener {
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
            if (diff > 40 || LastSpeed.get(event.getPlayer()) > 0.20) {
                AlarmUtil.AddViolation(event.getPlayer(), this, "Diff: " + diff + " Speed: " + LastSpeed.get(event.getPlayer()));
                event.getPlayer().sendMessage(ChatColor.GREEN + "Diff: " + diff + " Speed: " + LastSpeed.get(event.getPlayer()));
                event.getPlayer().sendMessage("EventPitch: " + event.getPlayer().getLocation().getPitch() + " LastPitch: " + LastPitch.get(event.getPlayer()));
            }
        }
    }
}