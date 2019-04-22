package me.sendpacket.anticheat.anticheat.Checks;

import me.sendpacket.anticheat.anticheat.Checks.Fly.Fly;
import me.sendpacket.anticheat.anticheat.Checks.Fly.Modes.*;
import me.sendpacket.anticheat.anticheat.Checks.KillAura.KillAura;
import me.sendpacket.anticheat.anticheat.Checks.KillAura.Modes.*;
import me.sendpacket.anticheat.anticheat.Checks.Other.*;
import me.sendpacket.anticheat.anticheat.Checks.Speed.Modes.Speed_FastSpeed;
import me.sendpacket.anticheat.anticheat.Checks.Speed.Modes.Speed_MiniHop;
import me.sendpacket.anticheat.anticheat.Checks.Speed.Speed;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class CheckManager{

    public static ArrayList<Check> CheckList = new ArrayList<>();

    public static Check Timer_Check = new Timer();
    public static Check Step_Check = new Step();
    public static Check Speed_Check = new Speed();
    public static Check FastUse_Check = new FastUse();
    public static Check InventoryMove_Check = new InventoryMove();
    public static Check NoFall_Check = new NoFall();
    public static Check Scaffold_Check = new Scaffold();
    public static Check NoGround_Check = new NoGround();
    public static Check NoSlowdown_Check = new NoSlowdown();
    public static Check Jesus_Check = new Jesus();
    public static Check Phase_Check = new Phase();
    public static Check KillAura_Check = new KillAura();
    public static Check AimAssist_Check = new AimAssist();
    public static Check Fly_Check = new Fly();

    public static SubCheck Speed_FastSpeed = new Speed_FastSpeed();
    public static SubCheck Speed_MiniHop = new Speed_MiniHop();

    public static SubCheck Fly_FallCycle = new Fly_FallCycle();
    public static SubCheck Fly_Gravity = new Fly_Gravity();
    public static SubCheck Fly_NoDown = new Fly_NoDown();
    public static SubCheck Fly_Speed = new Fly_Speed();
    public static SubCheck Fly_VerticalMotion = new Fly_VerticalMotion();

    public static SubCheck KillAura_AntiBotDetection = new KillAura_AntiBotDetection();
    public static SubCheck KillAura_BotHit = new KillAura_BotHit();
    public static SubCheck KillAura_CPS = new KillAura_CPS();
    public static SubCheck KillAura_Criticals = new KillAura_Criticals();
    public static SubCheck KillAura_KeepSprint = new KillAura_KeepSprint();
    public static SubCheck KillAura_Reach = new KillAura_Reach();
    public static SubCheck KillAura_WallHit = new KillAura_WallHit();

    public static boolean Enable_SetBacks = true;
    public static boolean Enable_Kicks = true;
    public static boolean Enable_Messages = false;

    public static void Load(JavaPlugin plugin)
    {
        CheckList.add(Speed_Check);
        CheckList.add(Fly_Check);
        CheckList.add(KillAura_Check);

        CheckList.add(Timer_Check);
        CheckList.add(Step_Check);
        CheckList.add(FastUse_Check);
        CheckList.add(InventoryMove_Check);
        CheckList.add(NoFall_Check);
        CheckList.add(NoGround_Check);
        CheckList.add(NoSlowdown_Check);
        CheckList.add(Jesus_Check);
        CheckList.add(Phase_Check);
        CheckList.add(AimAssist_Check);
        CheckList.add(Scaffold_Check);

        CallCheckOnEnable(); // Register events and setup schedulers
    }

    public static void CallCheckOnEnable()
    {
        Bukkit.getServer().getConsoleSender().sendMessage("§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-");
        for(Check c : CheckList) {
            c.onEnable();
            if (c.SubCheckList.size() > 0) {
                Bukkit.getServer().getConsoleSender().sendMessage("§7[§cAC/INFO§7]" + "§7 Check §c" + c.name + " §7(" + c.SubCheckList.size() + " modules) loaded.");
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage("§7[§cAC/INFO§7]" + "§7 Check " + c.name + " loaded.");
            }
        }
        for(Check c : CheckList) {
            for (SubCheck c2 : c.SubCheckList) {
                c2.onEnable();
                Bukkit.getServer().getConsoleSender().sendMessage("§7[§cAC/INFO§7] §7SubCheck §c" + c2.name + " §7from §c" + c.name + " §7loaded.");
            }
        }
        Bukkit.getServer().getConsoleSender().sendMessage("§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-§c-§7-");
    }

    public static Check getCheck(String name)
    {
        for(Check c : CheckList)
        {
            if(c.name.equalsIgnoreCase(name))
            {
                return c;
            }
        }
        return null;
    }


}
