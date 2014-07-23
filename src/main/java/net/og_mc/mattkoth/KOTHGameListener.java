/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import net.og_mc.mattmg.Kits;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import pw.ian.cloudgame.events.GameStartEvent;
import pw.ian.cloudgame.events.GameStopEvent;
import pw.ian.cloudgame.game.Game;
import pw.ian.cloudgame.gameplay.GameListener;

/**
 *
 * @author ian
 */
public class KOTHGameListener extends GameListener<KOTHState> {

    public KOTHGameListener(MattKOTH koth) {
        super(koth);
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        Game<KOTHState> game = game(event);
        if (game == null) {
            return;
        }
        KOTHState state = game.getState();
        for (Player p : state.getPlayers()) {
            if (state.isProvideArmor()) {
                Kits.loadEasyKit(p);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void postGameStart(GameStartEvent event) {
        Game<KOTHState> game = game(event);
        if (game == null) {
            return;
        }
        (new KOTHTimer(game)).runTimer();
    }

    @EventHandler
    public void onGameStop(GameStopEvent event) {
        Game<KOTHState> game = game(event);
        if (game == null) {
            return;
        }
        KOTHState state = game.getState();
        if (state.isStarted() && state.getCapturer() != null) {
            state.getCapturer().getInventory().setHelmet(state.getCapturerHelmet());
        }
    }

}
