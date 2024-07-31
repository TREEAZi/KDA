package org.treeazi.kda.KDASystem;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

import static org.treeazi.kda.BattleKDAMain.MODID;

public class PlayerKD {
    private ServerPlayer player;
    private int K = 0;
    private int D = 0;
    private Map<String, Player> victimMap = new HashMap<>();
    private Map<String, Player> killerMap = new HashMap<>();

    public PlayerKD(ServerPlayer pPlayer) {
        player = pPlayer;
    }

    public void Kill(ServerPlayer killPlayer){
        K++;
        victimMap.put(""+K, killPlayer);
    }

    public void Died(ServerPlayer Died){
        D++;
        killerMap.put(""+D, Died);
    }

    public void clearKD(){
        K = 0;
        D = 0;
        victimMap.clear();
        killerMap.clear();
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public int getD() {
        return D;
    }

    public int getK() {
        return K;
    }

    public Map<String, Player> getKillerMap() {
        return killerMap;
    }

    public Map<String, Player> getVictimMap() {
        return victimMap;
    }

    public String getInfo() {
        return player.getName().getString() + ": " + K + "/" + D;
    }

}