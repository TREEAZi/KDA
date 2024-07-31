package org.treeazi.kda.KDASystem;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static org.treeazi.kda.BattleKDAMain.MODID;

public class KDASystem {
    private static List<PlayerKD> PlayerList = new ArrayList<>();
    private static PlayerKD CurrentPlayer;

    public static void addPlayer(PlayerKD pPlayerKD){
        PlayerList.add(pPlayerKD);
    }

    public static void removePlayer(PlayerKD pPlayerKD){
        PlayerList.remove(pPlayerKD);
    }

    public static boolean playerExist(ServerPlayer pPlayer){
        for (PlayerKD playerKD : PlayerList){
            if (playerKD.getPlayer().equals(pPlayer)){
                CurrentPlayer = playerKD;
                return true;
            }
        }
        return false;
    }

    public static String allKDToString(){
        List<String> strList = new ArrayList<>();
        for (PlayerKD playerKD : PlayerList){
            strList.add(playerKD.getInfo());
        }
        return String.join("\n",strList);
    }

    public static String KDToString(ServerPlayer pPlayer){
        if (playerExist(pPlayer))
            return CurrentPlayer.getInfo();
        return "";
    }

    public static boolean clearKD(ServerPlayer pPlayer){
        if (playerExist(pPlayer)){
            CurrentPlayer.clearKD();
            return true;
        }
        else return false;
    }



    @Mod.EventBusSubscriber(modid = MODID)
    public static class PlayerKDEvent
    {
        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
        {
            addPlayer(new PlayerKD((ServerPlayer) event.getEntity()));
        }

        @SubscribeEvent
        public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event){
            if(playerExist((ServerPlayer) event.getEntity()))
                removePlayer(CurrentPlayer);
        }

        @SubscribeEvent
        public static void onPKillP(LivingDeathEvent event){
            Entity victim = event.getEntity();
            Entity killer = event.getSource().getEntity();
            if (victim instanceof ServerPlayer &&
                    killer instanceof ServerPlayer){
                for (PlayerKD playerKD : PlayerList){
                    if (playerKD.getPlayer().equals(victim)){
                        playerKD.Died((ServerPlayer) killer);
                        if (playerExist((ServerPlayer) killer))
                            CurrentPlayer.Kill((ServerPlayer) victim);
                    }
                }
            }
        }
    }
}
