package me.sendpacket.anticheat.anticheat.Analyzer;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AnalyzePlayer {

    public AnalyzePlayer(Player p)
    {
        this.player = p;
    }

    public ArrayList<AnalyzeData> Data = new ArrayList<>();
    public Player player;
}
