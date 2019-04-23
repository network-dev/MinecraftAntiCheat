package me.sendpacket.anticheat.anticheat.Analyzer;

import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AnalyzeLogger {

    public static ArrayList<AnalyzePlayer> AnalyzePlayers = new ArrayList<>();

    public static AnalyzePlayer getAnalyzePlayer(Player p)
    {
        for(AnalyzePlayer ap : AnalyzePlayers)
        {
            if(ap.player.getUniqueId().equals(p.getUniqueId()))
            {
                return ap;
            }
        }

        AnalyzePlayer new_ap = new AnalyzePlayer(p);
        AnalyzePlayers.add(new_ap);

        return new_ap;
    }

    public static void LogPlayer(Player p, double speed)
    {
        AnalyzePlayer ap = getAnalyzePlayer(p);

        AnalyzeData new_data = new AnalyzeData(p.getLocation(), p.getFallDistance(), speed, p.getLocation().add(0,-1,0).getBlock().getType(), p.isOnGround(), PlayerUtil.IsInAir(p), p.getLocation().getBlock().getType() == Material.WATER);

        ap.Data.add(new_data);
    }

    public static String getAnalyzedLog()
    {
        String result = "";

        


        return result;
    }
}
