package me.sendpacket.anticheat.anticheat.Menu;

import me.sendpacket.anticheat.anticheat.Utils.MenuUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MenuEvents implements Listener {

    @EventHandler
    public void onMenuClose(InventoryCloseEvent event)
    {
        Player player = (Player) event.getPlayer();
        if(event.getInventory().getTitle().equalsIgnoreCase(MenuUtil.Menu_Title))
        {
            MenuUtil.HasInventoryOpen.put(player, false);
        }
    }

    @EventHandler
    public void onFakeMenuOpen(PlayerInteractEvent event)
    {
        if(event.getClickedBlock() != null)
        {
            MenuUtil.HasInventoryOpen.put(event.getPlayer(), false);
        }
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory().getTitle().length() > 0) {
                if (event.getClickedInventory().getTitle().equalsIgnoreCase(MenuUtil.Menu_Title)) {
                    if (MenuUtil.HasInventoryOpen.get(player) != null) {
                        if (MenuUtil.HasInventoryOpen.get(player) == true) {
                            MenuUtil.InventoryClick(player, event.getClickedInventory(), event.getSlot());
                            event.setCancelled(true);
                        }
                    } else {
                        MenuUtil.HasInventoryOpen.put(player, true);
                        MenuUtil.InventoryClick(player, event.getClickedInventory(), event.getSlot());
                        event.setCancelled(true);
                    }
                }
            }
        }catch (Exception e)
        {}
    }

}
