/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import com.simplyian.cloudgame.events.GameEndEvent;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.GameTask;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import me.confuser.barapi.BarAPI;
import static net.og_mc.mattkoth.KOTHConstants.CAPTURE_WIN_SECONDS;
import net.og_mc.mattkoth.KOTHState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
            BarAPI.setMessage(p, state.getPlayers().size() + " left in LMS!");
        }

        if (state.getPlayers().size() == 1) {
            Bukkit.getPluginManager().callEvent(new GameEndEvent(game));
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
            Bukkit.getPluginManager().callEvent(new GameEndEvent(game));
            return;
        }
    }

    private void announceTime(String time) {
        game.broadcast("There's " + time + " left!");
    }

}
