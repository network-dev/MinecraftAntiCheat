package me.sendpacket.anticheat.anticheat.Checks.Other;

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
import me.sendpacket.anticheat.anticheat.Utils.AlarmUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Timer extends Check implements Listener {
    public Timer() {
        super("Packets", "Checks if the player is sending more packets than normal.", CheckCategory.OTHER, 10);
    }

    public static HashMap<Player, Boolean> MovingPlayerList = new HashMap<>();
    public static HashMap<Player, Integer> FlyingPlayerPackets = new HashMap<>();
    public static HashMap<Player, Integer> MovingPlayerPackets = new HashMap<>();

    public void on20TickUpdate()
    {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if (FlyingPlayerPackets.get(p) != null && MovingPlayerPackets.get(p) != null) {
                int FlyingPackets = FlyingPlayerPackets.get(p);
                int MovingPackets = MovingPlayerPackets.get(p);

                if (MovingPlayerList.get(p) != null) {
                    if (MovingPlayerList.get(p) == false) {
                        if (FlyingPackets > 21) {
                            AlarmUtil.AddViolation(p, CheckManager.getCheck("Packets"), "Flying packet limit exceeded." + "Exceeded packets: " + (FlyingPackets - 21));
                        }
                        if(MovingPackets > 2) {
                            AlarmUtil.AddViolation(p, CheckManager.getCheck("Packets"), "Position packet limit exceeded." + "Exceeded packets: " + (MovingPackets - 2));
                        }
                    }
                    if (MovingPlayerList.get(p) == true) {
                        if (MovingPackets > 22) {
                            AlarmUtil.AddViolation(p, CheckManager.getCheck("Packets"), "Position packet limit exceeded." + "Exceeded packets: " + (MovingPackets - 22));
                        }
                    }
                }

                MovingPlayerList.put(p, false);
                FlyingPlayerPackets.put(p, 0);
                MovingPlayerPackets.put(p, 0);
            }
        }
    }

    public void onMoveEvent(PlayerMoveEvent event)
    {
        if (event.getFrom().distance(event.getTo()) > 0.f) {
            MovingPlayerList.put(event.getPlayer(), true);
        }
    }

    public void onPacketReceiving(PacketEvent event)
    {
        Player player = event.getPlayer();

        if (event.getPacket().getType() == PacketType.Play.Client.FLYING) {
            FlyingPlayerPackets.put(player, FlyingPlayerPackets.get(player) != null ? FlyingPlayerPackets.get(player) + 1 : 1);
        }

        if (event.getPacket().getType() == PacketType.Play.Client.POSITION_LOOK || event.getPacket().getType() == PacketType.Play.Client.POSITION) {
            MovingPlayerPackets.put(player, MovingPlayerPackets.get(player) != null ? MovingPlayerPackets.get(player) + 1 : 1);
        }
    }
}
