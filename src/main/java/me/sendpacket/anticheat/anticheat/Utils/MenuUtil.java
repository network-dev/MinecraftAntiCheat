package me.sendpacket.anticheat.anticheat.Utils;

import me.sendpacket.anticheat.anticheat.Managers.AnalyzerManager;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Managers.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuUtil {

    public static int Inventory_State;
    public static String Menu_Title = "AntiCheat";
    public static HashMap<Player, Boolean> HasInventoryOpen = new HashMap<>();
    public static HashMap<Player, Check> SelectedCheck = new HashMap<>();
    private static ItemStack LIME_DYE = new ItemStack(Material.INK_SACK, 1, DyeColor.LIME.getDyeData());
    private static ItemStack GRAY_DYE = new ItemStack(Material.INK_SACK, 1, DyeColor.GRAY.getDyeData());
    private static ItemStack RED_DYE = new ItemStack(Material.INK_SACK, 1, DyeColor.RED.getDyeData());
    private static ItemStack ORANGE_DYE = new ItemStack(Material.INK_SACK, 1, DyeColor.ORANGE.getDyeData());


    public static Inventory getMainInv()
    {
        Inventory inv = Bukkit.createInventory(null, 9, Menu_Title);
        inv.setItem(1, ItemUtil.getItem(Material.WATCH, "§aChecks", "§7Here you can enable or disable all the different checks/detection methods."));
        inv.setItem(4, ItemUtil.getItem(Material.PAPER, "§6Analyze", "§cFor developers or bugs only.\n§7Creates a text file to log and analyze the movement of a player,\n§7used to create new detection methods for movement related cheats."));
        inv.setItem(7, ItemUtil.getItem(Material.COMPASS, "§cSettings", "§7Here you can change some of the behaviour of the anticheat."));
        return inv;
    }
    public static Inventory getSettingsInv(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 9, Menu_Title);
        inv.setItem(0, ItemUtil.getItem(RED_DYE, "§cBack"));

        boolean debug_state = false;
        if(AlarmUtil.PlayerShouldDebug.get(p) != null)
        {
            debug_state = AlarmUtil.PlayerShouldDebug.get(p);
        }

        inv.setItem(2, ItemUtil.getItem(Material.SIGN, "§7Debug", "§7[ Status: "+ (debug_state ? "§aON" : "§cOFF" ) +" §7]\n§cFor developers or bugs only.\n§7Messages to players with information about movement."));
        inv.setItem(3, ItemUtil.getItem(Material.ENDER_PEARL, "§7Blocking", "§7[ Status: "+ (CheckManager.Enable_SetBacks ? "§aON" : "§cOFF" ) +" §7]\n§7If enabled, the anticheat will try to block the cheats."));
        inv.setItem(4, ItemUtil.getItem(Material.WOOD_DOOR, "§7Kicks", "§7[ Status: "+ (CheckManager.Enable_Kicks ? "§aON" : "§cOFF" ) +" §7]\n§7Kicks the player when a certain violation level is reached."));
        inv.setItem(5, ItemUtil.getItem(Material.BOOK, "§7Messages", "§7[ Status: "+ (CheckManager.Enable_Messages ? "§aON" : "§cOFF" ) +" §7]\n§7Sends messages when the anticheat detects a cheat being used."));
        return inv;
    }
    public static Inventory getSubChecksInv(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 45, Menu_Title);
        if(SelectedCheck.get(p) != null) {
            for (SubCheck sc : SelectedCheck.get(p).SubCheckList) {
                inv.addItem(ItemUtil.getItem(sc.enabled ? LIME_DYE : GRAY_DYE, "§7" + sc.name, "§7[ Status: " + (sc.enabled ? "§aON" : "§cOFF") + " §7]"));
            }
        }
        inv.setItem(36, ItemUtil.getItem(RED_DYE, "§cBack"));
        return inv;
    }
    public static Inventory getChecksInv()
    {
        Inventory inv = Bukkit.createInventory(null, 9, Menu_Title);
        inv.setItem(0, ItemUtil.getItem(RED_DYE, "§cBack"));
        inv.setItem(8, ItemUtil.getItem(RED_DYE, "§cBack"));
        inv.setItem(2, ItemUtil.getItem(Material.IRON_SWORD, "§7Combat", "§7All of the combat related checks."));
        inv.setItem(4, ItemUtil.getItem(Material.FEATHER, "§7Movement", "§7All of the movement related checks."));
        inv.setItem(6, ItemUtil.getItem(Material.EXP_BOTTLE, "§7Other", "§7All the checks that don't have a specific category."));
        return inv;
    }
    public static Inventory getCategoryChecksInv(CheckCategory category)
    {
        Inventory inv = Bukkit.createInventory(null, 45, Menu_Title);
        boolean was_last_bigcheck = false;
        for(Check c : CheckManager.CheckList) {
            if (c.category == category) {
                if(c.SubCheckList.size() > 0)
                {
                    was_last_bigcheck = true;
                }else{
                    if(was_last_bigcheck)
                    {
                        inv.addItem(ItemUtil.getItem(Material.STICK,"§7 "));
                        was_last_bigcheck = false;
                    }
                }
                ItemStack mat_i = (c.SubCheckList.size() > 0 ? ORANGE_DYE : (c.enabled ? LIME_DYE : GRAY_DYE));
                String name_i = ("§7" + c.name);
                inv.addItem(ItemUtil.getItem(mat_i,name_i, (c.SubCheckList.size() > 0 ? "" : (" §7[ Status: " + (c.enabled ? "§aON" : "§cOFF") + " §7]") + "\n§7"+c.description)));
            }
        }
        inv.setItem(36, ItemUtil.getItem(RED_DYE, "§cBack"));
        return inv;
    }
    public static void UpdateInventory(Player p)
    {
        p.closeInventory();
        switch(Inventory_State)
        {
            case 0:
                p.openInventory(getMainInv());
                break;
            case 1:
                p.openInventory(getChecksInv());
                break;
            case 2:
                p.openInventory(getCategoryChecksInv(CheckCategory.COMBAT));
                break;
            case 3:
                p.openInventory(getCategoryChecksInv(CheckCategory.MOVEMENT));
                break;
            case 4:
                p.openInventory(getCategoryChecksInv(CheckCategory.OTHER));
                break;
            case 5:
                p.openInventory(getSettingsInv(p));
                break;
            case 6:
                p.openInventory(getSubChecksInv(p));
                break;
            default:
                break;
        }
        MenuUtil.HasInventoryOpen.put(p, true);
    }
    public static void OpenInventory(Player p)
    {
        Inventory_State = 0;
        UpdateInventory(p);
    }
    public static void InventoryClick(Player player, Inventory inv, int slot) {
        try{
            int check_i = 0;
            String name_i = "";
            switch (Inventory_State) {
                case 0:
                    switch (inv.getItem(slot).getItemMeta().getDisplayName()) {
                        case "§aChecks":
                            Inventory_State = 1;
                            UpdateInventory(player);
                            break;
                        case "§6Analyze":
                            AnalyzerManager.is_enabled = !AnalyzerManager.is_enabled;
                            if(!AnalyzerManager.is_enabled)
                            {
                                player.sendMessage("§7[§cAC§7] §7Movements from all the players analyzed and saved.");
                                AnalyzerManager.OutputLog();
                            }else{
                                player.sendMessage("§7[§cAC§7] §7Analyzing...");
                            }
                            break;
                        case "§cSettings":
                            Inventory_State = 5;
                            UpdateInventory(player);
                            break;
                        default:
                            break;
                    }
                    break;
                case 1:
                    switch (inv.getItem(slot).getItemMeta().getDisplayName()) {
                        case "§cBack":
                            Inventory_State = 0;
                            UpdateInventory(player);
                            break;
                        case "§7Combat":
                            Inventory_State = 2;
                            UpdateInventory(player);
                            break;
                        case "§7Movement":
                            Inventory_State = 3;
                            UpdateInventory(player);
                            break;
                        case "§7Other":
                            Inventory_State = 4;
                            UpdateInventory(player);
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    for (Check c : CheckManager.CheckList) {
                        name_i = ("§7" + c.name);
                        if (name_i.equalsIgnoreCase(inv.getItem(slot).getItemMeta().getDisplayName())) {
                            if (c.SubCheckList.size() > 0) {
                                SelectedCheck.put(player, c);
                                Inventory_State = 6;
                                UpdateInventory(player);
                                break;
                            }else{
                                c.enabled = !c.enabled;
                                if(!c.enabled)
                                {
                                    c.onDisable();
                                }
                            }
                        }
                    }
                    if (inv.getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("§cBack")) {
                        Inventory_State = 1;
                    }

                    UpdateInventory(player);
                    break;
                case 3:
                    for (Check c : CheckManager.CheckList) {
                        name_i = ("§7" + c.name);
                        if (name_i.equalsIgnoreCase(inv.getItem(slot).getItemMeta().getDisplayName())) {
                            if (c.SubCheckList.size() > 0) {
                                SelectedCheck.put(player, c);
                                Inventory_State = 6;
                                UpdateInventory(player);
                                break;
                            }else{
                                c.enabled = !c.enabled;
                                if(!c.enabled)
                                {
                                    c.onDisable();
                                }
                            }
                        }
                    }
                    if (inv.getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("§cBack")) {
                        Inventory_State = 1;
                    }

                    UpdateInventory(player);
                    break;
                case 4:
                    for (Check c : CheckManager.CheckList) {
                        name_i = ("§6" + c.name) + (c.SubCheckList.size() > 0 ? "" : (" §7[" + (c.enabled ? "§aON" : "§cOFF") + "§7]"));
                        if (name_i.equalsIgnoreCase(inv.getItem(slot).getItemMeta().getDisplayName())) {
                            if (c.SubCheckList.size() > 0) {
                                SelectedCheck.put(player, c);
                                Inventory_State = 6;
                                UpdateInventory(player);
                                break;
                            }else{
                                c.enabled = !c.enabled;
                                if(!c.enabled)
                                {
                                    c.onDisable();
                                }
                            }
                        }
                    }
                    if (inv.getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("§cBack")) {
                        Inventory_State = 1;
                    }

                    UpdateInventory(player);
                    break;
                case 5:
                    switch (inv.getItem(slot).getItemMeta().getDisplayName()) {
                        case "§cBack":
                            Inventory_State = 0;
                            UpdateInventory(player);
                            break;
                        case "§7Debug":
                            AlarmUtil.PlayerShouldDebug.put(player, (AlarmUtil.PlayerShouldDebug.get(player) != null ? !AlarmUtil.PlayerShouldDebug.get(player) : true));
                            UpdateInventory(player);
                            break;
                        case "§7Blocking":
                            CheckManager.Enable_SetBacks = !CheckManager.Enable_SetBacks;
                            UpdateInventory(player);
                            break;
                        case "§7Kicks":
                            CheckManager.Enable_Kicks = !CheckManager.Enable_Kicks;
                            UpdateInventory(player);
                            break;
                        case "§7Messages":
                            CheckManager.Enable_Messages = !CheckManager.Enable_Messages;
                            UpdateInventory(player);
                            break;
                        default:
                            break;
                    }
                    UpdateInventory(player);
                    break;
                case 6:
                    if(SelectedCheck.get(player) != null) {
                        for (SubCheck c : SelectedCheck.get(player).SubCheckList) {
                            if (slot == check_i) {
                                c.enabled = !c.enabled;
                                if(!c.enabled)
                                {
                                    c.onDisable();
                                }
                            }
                            check_i += 1;
                        }
                        if (inv.getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("§cBack")) {
                            switch(SelectedCheck.get(player).category)
                            {
                                case COMBAT:
                                    Inventory_State = 2;
                                    break;
                                case MOVEMENT:
                                    Inventory_State = 3;
                                    break;
                                case OTHER:
                                    Inventory_State = 4;
                                    break;
                            }
                        }
                    }
                    UpdateInventory(player);
                    break;
            }
        }catch (Exception e) {}
    }
}
