package me.sendpacket.anticheat.anticheat.Checks.KillAura.Modes;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.sendpacket.anticheat.anticheat.AntiCheat;
import me.sendpacket.anticheat.anticheat.Checks.Check;
import me.sendpacket.anticheat.anticheat.Checks.CheckCategory;
import me.sendpacket.anticheat.anticheat.Checks.CheckManager;
import me.sendpacket.anticheat.anticheat.Checks.KillAura.KillAura;
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.NPC;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class KillAura_AntiBotDetection extends SubCheck implements Listener {
    public KillAura_AntiBotDetection() {
        super("KillAura AntiBotDetection", CheckManager.KillAura_Check);
    }

    public static HashMap<Player, NPC> BotList = new HashMap<>();
    public static HashMap<Player, Integer> BotTimer = new HashMap<>();
    public static HashMap<Player, Boolean> BotHit = new HashMap<>();
    public static HashMap<Player, Boolean> DidBotCheck = new HashMap<>();
    public static HashMap<Player, Integer> CombatTimer = new HashMap<>();
    public static HashMap<Player, Integer> Hits = new HashMap<>();

    public void onTickUpdate() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (BotList.get(p) != null) {
                BotList.get(p).teleport(PlayerUtil.getBlockBehindPlayer(p, -1), false);
            }
            CombatTimer.put(p, CombatTimer.get(p) != null ? CombatTimer.get(p) + 1 : 1);
            if (CombatTimer.get(p) > 10) {
                Hits.put(p, 0);
            }
            if (CombatTimer.get(p) > 40) {
                if (BotList.get(p) != null) {
                    BotList.get(p).destroy();
                    BotList.put(p, null);
                }
                DidBotCheck.put(p, false);
            }

        }
    }

    public void onEnityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player)
        {
            Player player = (Player)event.getDamager();
            Player entity = (Player)event.getEntity();

            CombatTimer.put(player, 0);

            Hits.put(player, Hits.get(player) != null ? Hits.get(player) + 1 : 1);

            if(Hits.get(player) != null)
            {
                if(Hits.get(player) > 1)
                {
                    if(DidBotCheck.get(player) != null)
                    {
                        if(DidBotCheck.get(player) == false)
                        {
                            if(BotList.get(player) == null)
                            {
                                NPC new_npc = new NPC(player.getName(), player.getLocation(), AntiCheat.Plugin);
                                BotHit.put(player, false);
                                new_npc.setRecipientType(NPC.Recipient.LISTED_RECIPIENTS);
                                new_npc.addRecipient(player);
                                NPC.Action new_act = new NPC.Action();
                                new_act.setInvisible(true);
                                new_npc.setAction(new_act);
                                new_npc.teleport(PlayerUtil.getBlockBehindPlayer(player, -1), false);
                                new_npc.spawn(false, false);
                                BotList.put(player, new_npc);
                            }else{
                                BotTimer.put(player, BotTimer.get(player) != null ? BotTimer.get(player) + 1 : 1);
                                if(BotTimer.get(player) > 10) {
                                    if (BotHit.get(player) != null) {
                                        if (BotHit.get(player) == false) {
                                            AlarmUtil.AddViolation(player, CheckManager.KillAura_Check, "KillAura AntiBotDetection.");
                                        }
                                    }
                                    BotTimer.put(player, 0);
                                    DidBotCheck.put(player, true);
                                    BotList.get(player).destroy();
                                    BotList.put(player, null);
                                    BotHit.put(player, false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void onPacketReceiving(PacketEvent event) {
        if(event.getPacket().getType().equals(PacketType.Play.Client.USE_ITEM)) {
            Player player = event.getPlayer();

            int targetID = event.getPacket().getIntegers().read(0);

            if (BotList.get(player) != null) {
                if (targetID == BotList.get(player).getEntityId()) {
                    if (player.getAllowFlight())
                        return;
                    DidBotCheck.put(player, true);
                    BotHit.put(player, true);
                    BotList.get(player).destroy();
                    BotList.put(player, null);
                    CombatTimer.put(player, 0);
                }
            }
        }
    }
}