/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import com.simplyian.cloudgame.events.GameQuitEvent;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.GameListener;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author ian
 */
public class LMSDeathListener extends GameListener<HostedFFAState> {

    public LMSDeathListener(MattLMS koth) {
        super(koth);
    }

    @EventHandler
    public void onGameQuit(GameQuitEvent e) {
        Game<HostedFFAState> game = game(e);
        if (game == null) {
            return;
        }

        e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        game.getGameplay().sendGameMessage(e.getPlayer(), "You died with " + game.getState().getPlayers().size() + " left!");
    }

}
