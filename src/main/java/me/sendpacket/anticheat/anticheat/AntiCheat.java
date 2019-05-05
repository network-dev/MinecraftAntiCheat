package me.sendpacket.anticheat.anticheat;

import me.sendpacket.anticheat.anticheat.Managers.AnalyzerManager;
import me.sendpacket.anticheat.anticheat.Managers.CheckManager;
import me.sendpacket.anticheat.anticheat.Menu.MenuEvents;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.MenuUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiCheat extends JavaPlugin implements Listener {

    public static JavaPlugin Plugin;

    @Override
    public void onEnable() {
       this.Plugin = this; // To be used by other classes
       if(!this.getDataFolder().exists())
       {
           this.getDataFolder().mkdirs();
       }
       Bukkit.getServer().getPluginManager().registerEvents(new PluginListener(), this);
       Bukkit.getPluginManager().registerEvents(new MenuEvents(), this); // Used to handle interactions with the menu
       Bukkit.getPluginManager().registerEvents(new AlarmUtil(), this);
       Bukkit.getPluginManager().registerEvents(new AnalyzerManager(), this);
       PluginListener.Setup();
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
