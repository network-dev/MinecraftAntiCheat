package me.sendpacket.anticheat.anticheat.Analyzer;

import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

class Pattern
{
    Pattern(double speed1, double speed2, double speed3)
    {
        this.speed1 = speed1;
        this.speed2 = speed2;
        this.speed3 = speed3;
    }
    public double speed1,speed2,speed3;
}

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

    public static ArrayList<String> getAnalyzedLog(Player p) {
        AnalyzePlayer ap = getAnalyzePlayer(p);
        String result = "";
        if (ap != null) {

            boolean same_underblock = true;
            boolean constant_speed = true;
            boolean fake_falling = !p.isOnGround();
            Pattern pattern_found = new Pattern(0,0,0);

            Material last_undermat = null;
            for (AnalyzeData ad : ap.Data)
            {
                if(last_undermat != null)
                {
                    if(last_undermat != ad.undermaterial)
                    {
                        same_underblock = false;
                    }
                }
                last_undermat = ad.undermaterial;
            }

            double last_speed = 999.0D;
            for (AnalyzeData ad : ap.Data)
            {
                if(last_speed != 999.0D)
                {
                    if(last_speed != ad.speed)
                    {
                        constant_speed = false;
                    }
                }
                last_speed = ad.speed;
            }

            double falldist = 0.0D;
            for (AnalyzeData ad : ap.Data)
            {
                if(ad.falldist != 0.f)
                {
                    fake_falling = false;
                }
            }

            double llast_speed = 999.0D;
            double lllast_speed = 999.0D;
            for (AnalyzeData ad : ap.Data)
            {
                if(llast_speed != 999.0D && lllast_speed != 999.0D)
                {
                    if(ad.speed == lllast_speed)
                    {
                        pattern_found = new Pattern(ad.speed, llast_speed, lllast_speed);
                    }
                }

                lllast_speed = llast_speed;
                llast_speed = ad.speed;
            }


            result = "Results:  ";
            if(same_underblock) {
                result += "same_underblock ";
            }
            if(constant_speed) {
                result += "constant_speed ";
            }
            if(fake_falling) {
                result += "fake_falling ";
            }
            if(pattern_found.speed3 != 0.0 || pattern_found.speed2 != 0.0 || pattern_found.speed1 != 0.0)
            {
                result += "speed_pattern (" + pattern_found.speed1 + "-" + pattern_found.speed2 + "-" + pattern_found.speed3 + ")";
            }
        }

        ArrayList<String> returnlist = new ArrayList<>();
        returnlist.add(result+"\n");
        returnlist.add("----------------------\n");
        for (AnalyzeData ad : ap.Data)
        {
            returnlist.add("speed: " + ad.speed + " falldist: " + ad.falldist + " ground: " + ad.ground + " in_air: " + ad.in_air + " in_water: " + ad.in_water + " location: (" + ad.loc.getX() + "," + ad.loc.getY() + "," + ad.loc.getZ() + ")" + " under_material: " + ad.undermaterial + "\n");
        }
        return returnlist;
    }
}
