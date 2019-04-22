package me.sendpacket.anticheat.anticheat.Utils;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.Item;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class PlayerUtil {

    public static Random rand = new Random();

   public static Material[] StairsBlocks = { Material.SANDSTONE_STAIRS, Material.COBBLESTONE_STAIRS, Material.BRICK_STAIRS, Material.SMOOTH_STAIRS, Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.WOOD_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.JUNGLE_WOOD_STAIRS, Material.ACACIA_STAIRS, Material.DARK_OAK_STAIRS, Material.RED_SANDSTONE_STAIRS };

    public static float DistanceToGround(Player p)
    {
        float distance = 0;
        for(float i = 0.1f; i < 256.f; i+=0.1f)
        {
            if(IsSameBlockAround(p, Material.AIR, -(i), 0.5f))
            {
                distance += 0.1f;
            }else{
                break;
            }
        }
        return distance;
    }

    public static boolean isHalfBlock(Block b)
    {
        for(Material bb : StairsBlocks)
        {
            if(b.getType() == bb)
            {
                return true;
            }
        }

        if(b.getType() == Material.STEP)
        {
            return true;
        }

        return false;
    }

    public static Location getBlockBehindPlayer(Player player, int dist) {
        Vector inverseDirectionVec = player.getLocation().getDirection().normalize().multiply(-(dist));
        return player.getLocation().add(inverseDirectionVec);
    }

    public static float[] getAnglesToEntity(Player player, LivingEntity entity)
    {
        float xDiff = (float)(entity.getLocation().getX() - player.getLocation().getX());
        float yDiff = (float)(entity.getLocation().getY() + entity.getEyeHeight() - player.getHeight());
        float zDiff = (float)(entity.getLocation().getZ() - player.getLocation().getZ());
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D - 90.0D);
        float pitch = (float)-Math.toDegrees(Math.atan(yDiff / Math.sqrt(zDiff * zDiff + xDiff * xDiff)));
        return new float[] {yaw, pitch };
    }
    public static float[] getAnglesToEntityCenter(Player player, LivingEntity entity)
    {
        float xDiff = (float)(entity.getLocation().getX() - player.getLocation().getX());
        float yDiff = (float)(entity.getEyeHeight() - (entity.getHeight() / 2.0D) - player.getHeight());
        float zDiff = (float)(entity.getLocation().getZ() - player.getLocation().getZ());
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D - 90.0D);
        float pitch = (float)-Math.toDegrees(Math.atan(yDiff / Math.sqrt(zDiff * zDiff + xDiff * xDiff)));
        return new float[] {yaw, pitch };
    }

    public static double ClampYaw(double value) {
        value = value % 360.0D;

        if (value >= 180.0D) {
            value -= 360.0D;
        }

        if (value < -180.0D) {
            value += 360.0D;
        }

        return value;
    }

    public static float ClampYaw(float value) {
        value = value % 360.0F;

        if (value >= 180.0F) {
            value -= 360.0F;
        }

        if (value < -180.0F) {
            value += 360.0F;
        }

        return value;
    }

    public static net.minecraft.server.v1_12_R1.ItemStack getRandomArmor(int inv)
    {
        net.minecraft.server.v1_12_R1.ItemStack item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.AIR.getId()));;
        int n = rand.nextInt(6);
        switch(inv)
        {
            case 103:
                switch(n)
                {
                    case 0:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.DIAMOND_HELMET.getId()));
                        break;
                    case 1:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.IRON_HELMET.getId()));
                        break;
                    case 2:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.GOLD_HELMET.getId()));
                        break;
                    case 3:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.CHAINMAIL_HELMET.getId()));
                        break;
                    case 4:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.LEATHER_HELMET.getId()));
                        break;
                }
                break;
            case 102:
                switch(n)
                {
                    case 0:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.DIAMOND_CHESTPLATE.getId()));
                        break;
                    case 1:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.IRON_CHESTPLATE.getId()));
                        break;
                    case 2:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.GOLD_CHESTPLATE.getId()));
                        break;
                    case 3:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.CHAINMAIL_CHESTPLATE.getId()));
                        break;
                    case 4:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.LEATHER_CHESTPLATE.getId()));
                        break;
                }
                break;
            case 101:
                switch(n)
                {
                    case 0:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.DIAMOND_LEGGINGS.getId()));
                        break;
                    case 1:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.IRON_LEGGINGS.getId()));
                        break;
                    case 2:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.GOLD_LEGGINGS.getId()));
                        break;
                    case 3:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.CHAINMAIL_LEGGINGS.getId()));
                        break;
                    case 4:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.LEATHER_LEGGINGS.getId()));
                        break;
                }
                break;
            case 100:
                switch(n)
                {
                    case 0:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.DIAMOND_BOOTS.getId()));
                        break;
                    case 1:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.IRON_BOOTS.getId()));
                        break;
                    case 2:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.GOLD_BOOTS.getId()));
                        break;
                    case 3:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.CHAINMAIL_BOOTS.getId()));
                        break;
                    case 4:
                        item = new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(Material.LEATHER_BOOTS.getId()));
                        break;
                }
                break;
        }
        return item;
    }

    public static String getRandomOnlinePlayerName(Player avoid)
    {
        int n = rand.nextInt(Bukkit.getOnlinePlayers().size());
        int o = 0;
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(o == n)
            {
                if(p.getUniqueId().equals(avoid.getUniqueId()))
                {
                    for(Player p2 : Bukkit.getOnlinePlayers())
                    {
                        if(!p2.getUniqueId().equals(avoid.getUniqueId()))
                        {
                            return p2.getName();
                        }
                    }
                }
                return p.getName();
            }
            o += 1;
        }
        return "error";
    }

    public static String getRandomOnlinePlayerName()
    {
        int n = rand.nextInt(Bukkit.getOnlinePlayers().size());
        int o = 0;
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(o == n)
            {
                return p.getName();
            }
            o += 1;
        }
        return "error";
    }

    public static boolean IsAtEdgeOfBlock(Player p)
    {
        Location loc = p.getLocation();
        if(Math.abs(loc.getX() - loc.getBlockX()) > 0.25 || Math.abs(loc.getZ() - loc.getBlockZ()) > 0.25)
        {
            return true;
        }
        return false;
    }

    public static boolean IsInAir(Player p) {
        if (p.getLocation().getBlock().getType() == Material.AIR && !p.isOnGround() && IsSameBlockAround(p, Material.AIR, -1, 0.f)) {
            return true;
        }
        return false;
    }

    public static boolean HalfBlockUnder(Player p)
    {
        if (p.getLocation().getBlock().getType() == Material.STEP || p.getLocation().add(0, -1, 0).getBlock().getType() ==  Material.STEP || p.getLocation().add(0, -0.5, 0).getBlock().getType() ==  Material.STEP) {
            return true;
        }
        for(Material mat2 : StairsBlocks) {
            if (p.getLocation().getBlock().getType() == mat2 || p.getLocation().add(0, -1, 0).getBlock().getType() == mat2 || p.getLocation().add(0, -0.5, 0).getBlock().getType() == mat2) {
                return true;
            }
        }

        return false;
    }

    public static boolean IsClimbingVineOrLadder(Player p)
    {
        if(p.getLocation().getBlock().getType() == Material.LADDER || p.getLocation().getBlock().getType() == Material.VINE ||
           p.getLocation().add(0,-1,0).getBlock().getType() == Material.LADDER || p.getLocation().add(0,-1,0).getBlock().getType() == Material.VINE)
        {
            return true;
        }
        return false;
    }

    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return "W";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NW";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "N";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "NE";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "E";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SE";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "S";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "SW";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "W";
        } else {
            return null;
        }
    }

    public static Location getLocationFront(Player p, double distance)
    {
        Location loc = p.getLocation();
        switch(getCardinalDirection(p))
        {
            case "N":
                loc = p.getLocation().add(0,0,-distance);
                break;
            case "NE":
                loc = p.getLocation().add(distance,0,-distance);
                break;
            case "E":
                loc = p.getLocation().add(distance,0,0);
                break;
            case "SE":
                loc = p.getLocation().add(distance,0,distance);
                break;
            case "S":
                loc = p.getLocation().add(0,0,distance);
                break;
            case "SW":
                loc = p.getLocation().add(-distance,0,distance);
                break;
            case "W":
                loc = p.getLocation().add(-distance,0,0);
                break;
            case "NW":
                loc = p.getLocation().add(-distance,0,-distance);
                break;
            default:
                loc = p.getLocation();
                break;
        }
        return loc;
    }

    public static boolean IsWallInTheWay(Player p, LivingEntity ent, float distance)
    {
        for(float i = 0.f; i < distance; i++)
        {
            Location loc;
            switch(getCardinalDirection(p))
            {
                case "N":
                    loc = p.getLocation().add(0,0,-i);
                    break;
                case "NE":
                    loc = p.getLocation().add(i,0,-i);
                case "E":
                    loc = p.getLocation().add(i,0,0);
                    break;
                case "SE":
                    loc = p.getLocation().add(i,0,i);
                    break;
                case "S":
                    loc = p.getLocation().add(0,0,i);
                    break;
                case "SW":
                    loc = p.getLocation().add(-i,0,i);
                    break;
                case "W":
                    loc = p.getLocation().add(-i,0,0);
                    break;
                case "NW":
                    loc = p.getLocation().add(-i,0,-i);
                    break;
                default:
                    loc = p.getLocation();
                    break;
            }
            if(loc.getBlock().getType() != Material.AIR)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean IsSameBlockAround(Player p, Material mat, float add_y, float width)
    {
        Material new_loc1 = p.getLocation().add(0,add_y,0).getBlock().getType();

        Material new_loc2 = p.getLocation().add(width,add_y,0).getBlock().getType();
        Material new_loc3 = p.getLocation().add(-width,add_y,0).getBlock().getType();

        Material new_loc4 = p.getLocation().add(0,add_y,width).getBlock().getType();
        Material new_loc5 = p.getLocation().add(0,add_y,-width).getBlock().getType();

        Material new_loc6 = p.getLocation().add(-width,add_y,-width).getBlock().getType();
        Material new_loc7 = p.getLocation().add(width,add_y,width).getBlock().getType();

        Material new_loc8 = p.getLocation().add(width,add_y,-width).getBlock().getType();
        Material new_loc9 = p.getLocation().add(-width,add_y,width).getBlock().getType();

        if(new_loc1 == mat && new_loc2 == mat
        && new_loc3 == mat && new_loc4 == mat
        && new_loc5 == mat && new_loc6 == mat
        && new_loc7 == mat && new_loc8 == mat
        && new_loc9 == mat)
        {
            return true;
        }else{
            return false;
        }
    }

    public static int getPing(Player p)
    {
        CraftPlayer craft_player = (CraftPlayer) p;
        EntityPlayer ent_player = craft_player.getHandle();
        return ent_player.ping;
    }

    public static boolean IsLiquidAround(Player p, float add_y, float width)
    {
        boolean new_loc1 = p.getLocation().add(0,add_y,0).getBlock().isLiquid();

        boolean new_loc2 = p.getLocation().add(width,add_y,0).getBlock().isLiquid();
        boolean new_loc3 = p.getLocation().add(-width,add_y,0).getBlock().isLiquid();

        boolean new_loc4 = p.getLocation().add(0,add_y,width).getBlock().isLiquid();
        boolean new_loc5 = p.getLocation().add(0,add_y,-width).getBlock().isLiquid();

        boolean new_loc6 = p.getLocation().add(-width,add_y,-width).getBlock().isLiquid();
        boolean new_loc7 = p.getLocation().add(width,add_y,width).getBlock().isLiquid();

        boolean new_loc8 = p.getLocation().add(width,add_y,-width).getBlock().isLiquid();
        boolean new_loc9 = p.getLocation().add(-width,add_y,width).getBlock().isLiquid();

        if(new_loc1 && new_loc2 && new_loc3 && new_loc4 && new_loc5 && new_loc6 && new_loc7 && new_loc8 && new_loc9)
        {
            return true;
        }else{
            return false;
        }
    }
}
