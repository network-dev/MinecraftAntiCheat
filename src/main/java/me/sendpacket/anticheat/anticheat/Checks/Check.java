package me.sendpacket.anticheat.anticheat.Checks;

import com.comphenix.protocol.events.PacketEvent;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Check {
    public Check(String name, String description, CheckCategory category, int kick_max)
    {
        this.name = name; this.enabled = true; this.description = description; this.category = category; this.kick_max = kick_max;
    }

    //-Data-//
    public HashMap<Player, Integer> ViolationLevel = new HashMap<Player, Integer>();
    public ArrayList<SubCheck> SubCheckList = new ArrayList<>();
    public String name;
    public String description;
    public Boolean enabled;
    public Integer kick_max;
    public CheckCategory category;
    //-End-//

    public int getViolationLevel(Player p)
    {
        if(ViolationLevel.get(p) != null)
        {
            return ViolationLevel.get(p);
        }
        return 0;
    }
    public void ResetViolationLevel(Player p)
    {
        ViolationLevel.put(p, 0);
    }
    public void AddViolationLevel(Player p)
    {
        ViolationLevel.put(p, ViolationLevel.get(p) != null ? ViolationLevel.get(p) + 1 : 1);
    }

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
