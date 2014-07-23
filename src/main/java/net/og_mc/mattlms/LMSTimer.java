/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pw.ian.cloudgame.events.GameEndEvent;
import pw.ian.cloudgame.game.Game;
import pw.ian.cloudgame.gameplay.GameTask;
import pw.ian.cloudgame.gameplay.hostedffa.HostedFFAState;
import pw.ian.cloudgame.gameplay.hostedffa.HostedFFAWinner;

/**
 *
 * @author ian
 */
public class LMSTimer extends GameTask<HostedFFAState> {

    private final Game<HostedFFAState> game;

    private int announceCount = 0;

    public LMSTimer(Game<HostedFFAState> game) {
        super(game);
        this.game = game;
    }

    @Override
    public void run() {
        HostedFFAState state = game.getState();

        for (Player p : state.getParticipants()) {
            BarAPI.setMessage(p, ChatColor.AQUA + "" + state.getPlayers().size() + ChatColor.DARK_AQUA + " left in LMS!");
        }

        if (state.getPlayers().size() <= 1) {
            Player winner = null;
            if (state.getPlayers().size() == 1) {
                winner = state.getPlayers().get(0);
            }
            Bukkit.getPluginManager().callEvent(new GameEndEvent(game, new HostedFFAWinner(winner)));
            return;
        }

        int secsLeft = game.getState().remainingTime();
        if (secsLeft <= 25 * 60 && announceCount == 0) {
            if (game.getState().getMins() > 25) {
                announceTime("25 minutes");
            }
            announceCount++;
        } else if (secsLeft <= 20 * 60 && announceCount == 1) {
            if (game.getState().getMins() > 20) {
                announceTime("20 minutes");
            }
            announceCount++;
        } else if (secsLeft <= 15 * 60 && announceCount == 2) {
            if (game.getState().getMins() > 15) {
                announceTime("15 minutes");
            }
            announceCount++;
        } else if (secsLeft <= 10 * 60 && announceCount == 3) {
            if (game.getState().getMins() > 10) {
                announceTime("10 minutes");
            }
            announceCount++;
        } else if (secsLeft <= 7 * 60 && announceCount == 4) {
            if (game.getState().getMins() > 7) {
                announceTime("7 minutes");
            }
            announceCount++;
        } else if (secsLeft <= 5 * 60 && announceCount == 5) {
            if (game.getState().getMins() > 5) {
                announceTime("5 minutes");
            }
            announceCount++;
        } else if (secsLeft <= 3 * 60 && announceCount == 6) {
            if (game.getState().getMins() > 3) {
                announceTime("3 minutes");
            }
            announceCount++;
        } else if (secsLeft <= 1 * 60 && announceCount == 7) {
            if (game.getState().getMins() > 1) {
                announceTime("1 minute");
            }
            announceCount++;
        } else if (secsLeft <= 0 * 60 && announceCount == 8) {
            Bukkit.getPluginManager().callEvent(new GameEndEvent(game, null));
            return;
        }
    }

    private void announceTime(String time) {
        game.broadcast("There's " + time + " left!");
    }

}
