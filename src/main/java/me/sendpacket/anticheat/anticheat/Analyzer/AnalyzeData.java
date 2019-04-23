package me.sendpacket.anticheat.anticheat.Analyzer;

import org.bukkit.Location;
import org.bukkit.Material;

public class AnalyzeData {

    public AnalyzeData(Location loc, double falldist, double speed, Material undermaterial, boolean ground, boolean in_air, boolean in_water)
    {
        this.loc = loc;
        this.falldist = falldist;
        this.speed = speed;
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
