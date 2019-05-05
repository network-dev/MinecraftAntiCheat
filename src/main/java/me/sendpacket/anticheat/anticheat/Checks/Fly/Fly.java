package me.sendpacket.anticheat.anticheat.Checks.Fly;

import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Managers.CheckManager;

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