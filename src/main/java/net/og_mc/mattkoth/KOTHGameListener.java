/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import com.simplyian.cloudgame.events.GameEndEvent;
import com.simplyian.cloudgame.events.GameQuitEvent;
import com.simplyian.cloudgame.events.GameStartEvent;
import com.simplyian.cloudgame.events.GameStopEvent;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

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

        for (Player p : Bukkit.getOnlinePlayers()) {
            getGameplay().sendBanner(p, "A KOTH on map " + ChatColor.DARK_GREEN + game.getArena().getName() + " " + ChatColor.GREEN + "has started!",
                    "Type " + ChatColor.DARK_GREEN + "/koth spectate " + ChatColor.GREEN + "to spectate it!");
        }

        KOTHState state = game.getState();
        for (Player p : state.getPlayers()) {
            Location spawn = game.getArena().getNextSpawn();
            p.teleport(spawn);
        }

        state.setStarted();
        (new KOTHTimer(game)).runTimer();
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        Game<KOTHState> game = game(event);
        if (game == null) {
            return;
        }

        Player winner = game.getState().getCapturer();
        if (winner == null) {
            game.broadcast("Game over! Nobody won!");
        } else {
            game.broadcast(ChatColor.YELLOW + winner.getName() + ChatColor.RED + " has won the KOTH!");
            getGameplay().sendGameMessage(winner, "To redeem your prize, type " + ChatColor.YELLOW + "/koth redeem" + ChatColor.RED + "!");
            ((MattKOTH) getGameplay()).addPrize(winner);
        }

        game.stop();
    }

    @EventHandler
    public void onGameStop(GameStopEvent event) {
        Game<KOTHState> game = game(event);
        if (game == null) {
            return;
        }
        KOTHState state = game.getState();

        if (!state.isStarted()) {
            game.broadcast("The KOTH has been cancelled.");
        } else {
            if (state.getCapturer() != null) {
                state.getCapturer().getInventory().setHelmet(state.getCapturerHelmet());
            }

            for (Player player : state.getSpectators()) {
                getGameplay().getPlugin().getPlayerStateManager().loadState(player);
            }

            for (Player player : state.getParticipants()) {
                Bukkit.getPluginManager().callEvent(new GameQuitEvent(game, player));
            }
        }
        state.setOver();
        getGameplay().getPlugin().getGameManager().removeGame(game);
        ((MattKOTH) getGameplay()).setGame(null);
    }

}
