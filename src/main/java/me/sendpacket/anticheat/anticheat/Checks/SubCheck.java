package me.sendpacket.anticheat.anticheat.Checks;

import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class SubCheck {
    public SubCheck(String name, Check check)
    {
        this.name = name; this.enabled = true; this.main_check = check;
    }

    //-Data-//
    public String name;
    public Boolean enabled;
    public Check main_check;
    //-End-//

    public void onEnable(){};
    public void onDisable(){};
    public void onMoveEvent(PlayerMoveEvent event){};
    public void onInteract(PlayerInteractEvent event){};
    public void onEnityDamageByEntity(EntityDamageByEntityEvent event){};
    public void onEntityDamageEvent(EntityDamageEvent event){};
    public void onBlockPlace(BlockPlaceEvent event){};
    public void onItemConsume(PlayerItemConsumeEvent event){};
    public void onEntityShootBow(EntityShootBowEvent event){};
    public void onInventoryMove(InventoryClickEvent event){};
    public void onTickUpdate(){};
    public void on20TickUpdate(){};
    public void onPacketReceiving(PacketEvent event) {};
    public void onPacketSending(PacketEvent event) {};
}
