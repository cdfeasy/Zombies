package zombies.server.game.play;

import zombies.dto.builder.AchievementsReplyBuilder;
import zombies.dto.builder.TurnReplyBuilder;
import com.google.inject.Inject;
import zombies.dto.reply.TurnReply;
import zombies.server.base.DTOWorker;
import zombies.server.game.LobbyManager;
import zombies.server.game.UserInfo;
import zombies.entity.support.GameInfo;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 05.03.13
 * Time: 20:37
 * To change this template use File | Settings | File Templates.
 */
public class GameEndProcessor {
    @Inject
    LobbyManager manager;

    @Inject
    DTOWorker dto;

    private int[] xp=new int[300];

    public  GameEndProcessor(){
         for(int i=0;i<300;i++){
             int lvl=i+1;
             xp[i]= (lvl * (lvl - 1)/2) * 1_00;
         }

    }

    private int processLevel(int exp){
        for(int i=0;i<299;i++){
            if(exp>xp[i] && exp<xp[i+1]){
                return i+1;
            }
        }
        return 1;
    }
    private void processUserInfo(UserInfo player,GameInfo playerInfo) throws IOException {
        player.getUser().setXp(player.getUser().getXp() + playerInfo.getXp());
        player.getUser().setGold(player.getUser().getGold()+playerInfo.getGold());
        player.getUser().setSurvivalsKilled(player.getUser().getSurvivalsKilled()+playerInfo.getSurvivalsKilled());
        player.getUser().setZombieKilled(player.getUser().getZombieKilled() + playerInfo.getZombieKilled());
        int level=processLevel(player.getUser().getXp());
        if(level!=player.getUser().getLevel()){
            AchievementsReplyBuilder bld=new AchievementsReplyBuilder();
            bld.setType((byte)0).setValue(level).setDescription("You reach level" + level);
            manager.getRequestManager().sendReply(player, bld.build());
        }
        player.getUser().setLevel(level);


    }


    public void processEnd(UserInfo playerWin,UserInfo playerLoose, GameInfo playerWinInfo, GameInfo playerLooseInfo,TurnReplyBuilder turnReplyBuilder, GameManager gm ) throws IOException {
        gm.running.set(false);
        playerWinInfo.setWin(true);
        playerLooseInfo.setWin(false);
        int winExp=10-playerWin.getUser().getLevel()+playerLoose.getUser().getLevel()*100;
        int looseExp=(10-playerLoose.getUser().getLevel()+playerWin.getUser().getLevel())/2;
        int winGold=10+playerLoose.getUser().getLevel();
        int looseGold=(10+playerWin.getUser().getLevel())/2;

        playerWinInfo.setGold(winGold);
        playerWinInfo.setXp(winExp);

        playerLooseInfo.setGold(looseGold);
        playerLooseInfo.setXp(looseExp);

        turnReplyBuilder.setAction(TurnReply.actionEnum.uwin.ordinal());
        turnReplyBuilder.setInfo(playerWinInfo);
        manager.getRequestManager().sendReply(playerWin, turnReplyBuilder.build());
        turnReplyBuilder.setAction(TurnReply.actionEnum.ulose.ordinal());
        turnReplyBuilder.setInfo(playerLooseInfo);
        manager.getRequestManager().sendReply(playerLoose, turnReplyBuilder.build());

        processUserInfo(playerWin,playerWinInfo);
        processUserInfo(playerLoose,playerLooseInfo);

        dto.processGameResult(playerWin,playerLoose,playerWinInfo,playerLooseInfo);

        playerWin.setManager(null);
        playerLoose.setManager(null);
        manager.endGame(gm);

    }

}
