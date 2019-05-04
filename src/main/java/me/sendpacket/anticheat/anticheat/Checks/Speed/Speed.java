package me.sendpacket.anticheat.anticheat.Checks.Speed;

import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import org.bukkit.event.Listener;

public class Speed extends Check {
    public Speed() {
        super("Speed", "Checks if the player movement is faster than normal.", CheckCategory.MOVEMENT, 10);
    }

    public void onEnable() {
        SubCheckList.add(CheckManager.Speed_FastSpeed);
        SubCheckList.add(CheckManager.Speed_MiniHop);
    }
}
