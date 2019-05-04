package me.sendpacket.anticheat.anticheat.Checks.Fly;

import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.Fly.Modes.*;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class Fly extends Check {
    public Fly() {
        super("Fly", "Checks if the player is flying while not having permission.", CheckCategory.MOVEMENT, 10);
    }

    public void onEnable() {
        SubCheckList.add(CheckManager.Fly_FallCycle);
        SubCheckList.add(CheckManager.Fly_Gravity);
        SubCheckList.add(CheckManager.Fly_NoDown);
        SubCheckList.add(CheckManager.Fly_Speed);
        SubCheckList.add(CheckManager.Fly_VerticalMotion);
    }
}