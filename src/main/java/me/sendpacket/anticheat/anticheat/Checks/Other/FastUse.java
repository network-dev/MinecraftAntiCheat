package me.sendpacket.anticheat.anticheat.Checks.Other;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class FastUse extends Check implements Listener {
    public FastUse() {
        super("FastUse", "Checks if the player is using consumable items too fast.", CheckCategory.MOVEMENT, 10);
    }

    public static HashMap<Player, Long> PlayerList = new HashMap();

    public void onItemConsume(PlayerItemConsumeEvent event)
    {
        Player player = event.getPlayer();

        if(PlayerList.get(player) != null)
        {
            Long diff = System.currentTimeMillis() - PlayerList.get(player);
        }

        PlayerList.put(player, System.currentTimeMillis());
    }
}