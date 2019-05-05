package me.sendpacket.anticheat.anticheat.Checks.KillAura;

import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Managers.CheckManager;


public class KillAura extends Check {
    public KillAura() {
        super("KillAura", "Checks if the player has an advantage over other entities.", CheckCategory.COMBAT, 10);
    }

    public void onEnable() {
        SubCheckList.add(CheckManager.KillAura_BotHit);
        SubCheckList.add(CheckManager.KillAura_CPS);
        SubCheckList.add(CheckManager.KillAura_Criticals);
        SubCheckList.add(CheckManager.KillAura_KeepSprint);
        SubCheckList.add(CheckManager.KillAura_Reach);
        SubCheckList.add(CheckManager.KillAura_WallHit);
        SubCheckList.add(CheckManager.KillAura_AntiBotDetection);
    }
}
