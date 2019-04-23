package me.sendpacket.anticheat.anticheat.Analyzer;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.*;

public class AnalyzerManager implements Listener {

    public static boolean is_enabled = false;

    public static void WriteFile(Player p)
    {
        File file = new File(AntiCheat.Plugin.getDataFolder(), "Analyzed_" + p.getName() + ".txt");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for(String s : AnalyzeLogger.getAnalyzedLog(p))
            {
                bw.write(s);
            }
            bw.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void OutputLog()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            WriteFile(p);
        }

        AnalyzeLogger.AnalyzePlayers.clear();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (is_enabled) {
            Location temp_loc_player = new Location(player.getWorld(), event.getFrom().getX(), 0, event.getFrom().getZ());
            Location temp_loc_entity = new Location(player.getWorld(), event.getTo().getX(), 0, event.getTo().getZ());
            double clean_dist = temp_loc_player.distance(temp_loc_entity);
            AnalyzeLogger.LogPlayer(event.getPlayer(), clean_dist);
        }
    }
}
