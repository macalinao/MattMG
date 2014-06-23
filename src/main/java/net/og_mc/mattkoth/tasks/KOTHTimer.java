/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth.tasks;

import com.simplyian.cloudgame.events.GameEndEvent;
import com.simplyian.cloudgame.game.Game;
import java.util.UUID;
import me.confuser.barapi.BarAPI;
import net.og_mc.mattkoth.KOTHState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author ian
 */
public class KOTHTimer extends BukkitRunnable {

    private final Game<KOTHState> game;

    private int announceCount = 0;

    private UUID lastCapturer;

    public KOTHTimer(Game<KOTHState> game) {
        this.game = game;
    }

    @Override
    public void run() {
        updateCaptureTime();
        updateTime();
    }

    private void updateTime() {
        if (game.getState().isOver()) {
            cancel();
            return;
        }

        int secsLeft = game.getState().remainingTime();
        if (secsLeft <= 7 * 60 && announceCount == 0) {
            announceTime("7 minutes");
            announceCount++;
        } else if (secsLeft <= 5 * 60 && announceCount == 1) {
            announceTime("5 minutes");
            announceCount++;
        } else if (secsLeft <= 3 * 60 && announceCount == 2) {
            announceTime("3 minute");
            announceCount++;
        } else if (secsLeft <= 1 * 60 && announceCount == 3) {
            announceTime("1 minute");
            announceCount++;
        } else if (secsLeft <= 0 * 60 && announceCount == 4
                && (game.getState().secondsCaptured() == -1
                || game.getState().secondsCaptured() >= 120)) { // Overtime check
            Bukkit.getPluginManager().callEvent(new GameEndEvent(game));
            cancel();
        }
    }

    private void updateCaptureTime() {
        KOTHState state = game.getState();
        Player capturer = state.getCapturer();
        if (capturer == null || (lastCapturer != null && !lastCapturer.equals(capturer.getUniqueId()))) {
            for (Player player : state.getParticipants()) {
                BarAPI.setMessage(player, ChatColor.RED + "Nobody controls the hill!");
            }
            lastCapturer = (capturer == null) ? null : capturer.getUniqueId();
            return;
        }
        lastCapturer = capturer.getUniqueId();

        int secsLeft = 120 - state.secondsCaptured();
        for (Player player : state.getParticipants()) {
            BarAPI.setMessage(player,
                    ChatColor.GREEN + capturer.getName() + ChatColor.DARK_GREEN
                    + " wins in " + ChatColor.GREEN + secsLeft + " seconds"
                    + ChatColor.DARK_GREEN + "!", (float) secsLeft / 120f);
        }

        if (secsLeft <= 0) {
            Bukkit.getPluginManager().callEvent(new GameEndEvent(game));
            cancel();
        }
        return;
    }

    private void announceTime(String time) {
        game.broadcast("There's " + time + " left!");
    }

    private void announceCaptureTime(String time) {
        game.broadcast("There's " + time + " left until " + ChatColor.YELLOW + game.getState().getCapturer().getName() + ChatColor.RED + " wins!");
    }
}
