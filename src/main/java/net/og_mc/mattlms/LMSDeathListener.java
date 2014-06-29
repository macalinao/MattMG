/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import com.simplyian.cloudgame.gameplay.listeners.GameListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author ian
 */
public class LMSDeathListener extends GameListener<HostedFFAState> {

    public LMSDeathListener(MattLMS koth) {
        super(koth);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Game<HostedFFAState> game = game(e.getEntity());
        if (game == null) {
            return;
        }

        game.getGameplay().sendGameMessage(e.getEntity(), "You died with " + game.getState().getPlayers().size() + " left!");
    }

}
