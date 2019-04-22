package me.sendpacket.anticheat.anticheat.Checks.Other;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.sun.org.apache.xpath.internal.operations.Bool;
import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class NoSlowdown extends Check implements Listener {
    public NoSlowdown() {
        super("NoSlowdown", "Checks if the player is not slowing down while using items.", CheckCategory.MOVEMENT, 20);
    }

    public static HashMap<Player, Double> LastSpeed = new HashMap<>();
    public static HashMap<Player, Double> MaxDiff = new HashMap<>();

    public void onMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if (player.getAllowFlight())
            return;

        double clean_dist = MathUtil.GetDistanceXZ(event.getFrom().getX(), event.getFrom().getZ(), event.getTo().getX(), event.getTo().getZ());

        if(LastSpeed.get(player) != null)
        {
            double diff = MathUtil.GetDistanceBetween2Numbers(LastSpeed.get(player), clean_dist);
            if(MaxDiff.get(player) != null) {
                if (diff > MaxDiff.get(player)) {
                    MaxDiff.put(player, diff);
                }
            }else{
                MaxDiff.put(player, diff);
            }
        }
        LastSpeed.put(player, clean_dist);
    }
    public void onInteract(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
        {
            if(event.getItem() != null) {
                if (event.getItem().getType().isEdible() || event.getItem().getType() == Material.BOW || event.getPlayer().isBlocking()) {
                    LastSpeed.put(event.getPlayer(), null);
                    MaxDiff.put(event.getPlayer(), null);
                }
            }
        }
    }
    public void onItemConsume(PlayerItemConsumeEvent event)
    {
        Player player = event.getPlayer();
        if (MaxDiff.get(player) != null) {
            if(MaxDiff.get(player) != null) {
                if (MaxDiff.get(player) <= 0.01) {
                    AlarmUtil.AddViolation(player, CheckManager.getCheck("NoSlowdown"), "Curve: " + MaxDiff.get(player));
                }
            }
        }
    }
    public void onEntityShootBow(EntityShootBowEvent event)
    {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(MaxDiff.get(player) != null) {
                if (MaxDiff.get(player) <= 0.01) {
                    AlarmUtil.AddViolation(player, CheckManager.getCheck("NoSlowdown"), "Curve: " + MaxDiff.get(player));
                }
            }
        }
    }

    public void onPacketReceiving(PacketEvent event)
    {
        if(event.getPacket().getType().equals(PacketType.Play.Client.BLOCK_DIG))
        {
            Player player = event.getPlayer();

            if(!CheckManager.NoSlowdown_Check.enabled)
                return;

            if(player.getInventory().getItemInMainHand().getType().isEdible() || player.getInventory().getItemInOffHand().getType().isEdible()) {
                LastSpeed.put(event.getPlayer(), null);
                MaxDiff.put(event.getPlayer(), null);
            }
        }
    }
}

