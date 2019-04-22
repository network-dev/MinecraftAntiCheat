package me.sendpacket.anticheat.anticheat;

import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.Other.Timer;
import me.sendpacket.anticheat.anticheat.Menu.MenuEvents;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MenuUtil;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiCheat extends JavaPlugin implements Listener {

    public static JavaPlugin Plugin;

    @Override
    public void onEnable() {
       this.Plugin = this; // To be used by other classes
        //test
       Bukkit.getServer().getPluginManager().registerEvents(new me.sendpacket.anticheat.anticheat.Listener(), this);
       Bukkit.getPluginManager().registerEvents(new MenuEvents(), this); // Used to handle interactions with the menu
       Bukkit.getPluginManager().registerEvents(new AlarmUtil(), this);

       me.sendpacket.anticheat.anticheat.Listener.Setup();
       AlarmUtil.UpdateAvoidTimer();
       CheckManager.Load(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("ac"))
            {
                MenuUtil.HasInventoryOpen.put(player, true);
                MenuUtil.OpenInventory(player);
            }
        }
        return true;
    }
}
