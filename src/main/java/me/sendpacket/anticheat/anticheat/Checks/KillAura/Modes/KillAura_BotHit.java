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
import me.sendpacket.anticheat.anticheat.Checks.SubCheck;
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import me.sendpacket.anticheat.anticheat.Utils.NPC;
import me.sendpacket.anticheat.anticheat.Utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Random;

public class KillAura_BotHit  extends SubCheck implements Listener {
    public KillAura_BotHit() {
        super("KillAura BotHit", CheckManager.KillAura_Check);
    }

    public static HashMap<Player, NPC> PlayerNPC = new HashMap<>();
    public static HashMap<Player, Integer> NPCHits = new HashMap<>();
    public static HashMap<Player, Integer> NPCTimer = new HashMap<>();
    static Random rnd = new Random();

    public void onTickUpdate() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (PlayerNPC.get(p) != null) {
                PlayerNPC.get(p).teleport(PlayerUtil.getBlockBehindPlayer(p, 2), false);
            }
            NPCTimer.put(p, NPCTimer.get(p) != null ? NPCTimer.get(p) + 1 : 1);
            if (NPCTimer.get(p) > 10) {
                if (NPCHits.get(p) != null) {
                    if (NPCHits.get(p) > 2) {
                        AlarmUtil.AddViolation(p, CheckManager.KillAura_Check, "KillAura BotHit " + NPCHits.get(p) + " hits.");
                    }
                }
                NPCHits.put(p, 0);
                NPCTimer.put(p, 0);
            }
        }
    }

    public void onEnityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player player = (Player) event.getDamager();
            Player entity = (Player) event.getEntity();

            if(PlayerNPC.get(player) == null)
            {
                NPC new_npc = new NPC(player.getName(), PlayerUtil.getLocationFront(player, 1), AntiCheat.Plugin);
                new_npc.setRecipientType(NPC.Recipient.LISTED_RECIPIENTS);
                new_npc.addRecipient(player);
                NPC.Action new_act = new NPC.Action();
                new_act.setInvisible(true);
                new_npc.setAction(new_act);
                new_npc.spawn(false, false);
                PlayerNPC.put(player, new_npc);
            }
        }
    }

    public void onPacketReceiving(PacketEvent event)
    {
        if(event.getPacket().getType().equals(PacketType.Play.Client.USE_ENTITY))
        {
            Player player = event.getPlayer();

            int targetID = event.getPacket().getIntegers().read(0);

            if(PlayerNPC.get(player) != null)
            {
                if(targetID == PlayerNPC.get(player).getEntityId())
                {
                    if (player.getAllowFlight())
                        return;
                    NPCHits.put(player, NPCHits.get(player) != null ? NPCHits.get(player) + 1 : 1);
                }
            }
        }
    }
}