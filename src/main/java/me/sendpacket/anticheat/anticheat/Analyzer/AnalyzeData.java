package me.sendpacket.anticheat.anticheat.Analyzer;

import me.sendpacket.anticheat.anticheat.Utils.MathUtil;
import org.bukkit.Location;
import org.bukkit.Material;

public class AnalyzeData {

    public AnalyzeData(Location loc, double falldist, double speed, Material undermaterial, boolean ground, boolean in_air, boolean in_water)
    {
        Location temp_loc = loc;
        temp_loc.setX(MathUtil.RoundNumber(temp_loc.getX(), 3));
        temp_loc.setY(MathUtil.RoundNumber(temp_loc.getY(), 3));
        temp_loc.setZ(MathUtil.RoundNumber(temp_loc.getZ(), 3));
        this.loc = temp_loc;
        this.falldist = MathUtil.RoundNumber(falldist,3);
        this.speed = MathUtil.RoundNumber(speed, 3);
        this.undermaterial = undermaterial;
        this.ground = ground;
        this.in_air = in_air;
        this.in_water = in_water;
    }
    Location loc;
    double falldist, speed;
    Material undermaterial;
    boolean ground, in_air, in_water;
}
