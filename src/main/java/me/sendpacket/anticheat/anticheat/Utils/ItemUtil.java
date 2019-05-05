package me.sendpacket.anticheat.anticheat.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemUtil {

    public static ItemStack getItem(ItemStack is, String display_name, String description)
    {
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(display_name);
        ArrayList<String> lore = new ArrayList<String>();

        if(description.contains("\n"))
        {
            for(String splitted_string : description.split("\n"))
            {
                lore.add(splitted_string);
            }
        }else{
            lore.add(description);
        }

        meta.setLore(lore);
        is.setItemMeta(meta);

        return is;
    }

    public static ItemStack getItem(ItemStack is, String display_name)
    {
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(display_name);
        is.setItemMeta(meta);

        return is;
    }

    public static ItemStack getItem(Material mat, String display_name, String description)
    {
        ItemStack is = new ItemStack(mat);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(display_name);
        ArrayList<String> lore = new ArrayList<String>();

        if(description.contains("\n"))
        {
            for(String splitted_string : description.split("\n"))
            {
                lore.add(splitted_string);
            }
        }else{
            lore.add(description);
        }

        meta.setLore(lore);
        is.setItemMeta(meta);

        return is;
    }

    public static ItemStack getItem(Material mat, String display_name)
    {
        ItemStack is = new ItemStack(mat);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(display_name);
        is.setItemMeta(meta);

        return is;
    }

}
