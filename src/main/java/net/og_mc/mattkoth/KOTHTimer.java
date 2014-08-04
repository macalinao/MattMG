/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import java.util.UUID;
import me.confuser.barapi.BarAPI;
import static net.og_mc.mattkoth.MattKOTH.CAPTURE_WIN_SECONDS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pw.ian.cloudgame.events.GameEndEvent;
import pw.ian.cloudgame.game.Game;
import pw.ian.cloudgame.gameplay.GameTask;
import pw.ian.cloudgame.gameplay.hostedffa.HostedFFAWinner;

/**
 *
 * @author ian
 */
public class KOTHTimer extends GameTask<KOTHState> {

    private final Game<KOTHState> game;

    private int announceCount = 0;

    private UUID lastCapturer;

    public KOTHTimer(Game<KOTHState> game) {
        super(game);
        this.game = game;
    }

    @Override
    public void run() {
        KOTHState state = game.getState();

        // Put this up here so we can get rid of the bar
        int captureSecsLeft = CAPTURE_WIN_SECONDS - state.secondsCaptured();
        if (captureSecsLeft <= 0) {
            game.events().end(new HostedFFAWinner<KOTHState>(state.getCapturer()));
            return;
        }

        Player capturer = state.getCapturer();
        if (capturer == null || (lastCapturer != null && !lastCapturer.equals(capturer.getUniqueId()))) {
            for (Player player : state.getParticipants()) {
                BarAPI.setMessage(player, ChatColor.RED + "Nobody controls the hill!");
            }
            lastCapturer = (capturer == null) ? null : capturer.getUniqueId();
        } else {
            lastCapturer = capturer.getUniqueId();
            for (Player player : state.getParticipants()) {
                BarAPI.setMessage(player,
                        ChatColor.GREEN + capturer.getName() + ChatColor.DARK_GREEN
                        + " wins in " + ChatColor.GREEN + captureSecsLeft + " seconds"
                        + ChatColor.DARK_GREEN + "!", ((float) captureSecsLeft * 100) / ((float) CAPTURE_WIN_SECONDS));
            }
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
        } else if (secsLeft <= 0 * 60 && announceCount == 8
                && !game.getState().isCapturing()) {
            game.events().end(null);
            return;
        }
    }

    private void announceTime(String time) {
        game.broadcast("There's " + time + " left!");
    }

}
