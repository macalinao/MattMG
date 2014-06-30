/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import com.simplyian.cloudgame.events.GameEndEvent;
import com.simplyian.cloudgame.events.GameQuitEvent;
import com.simplyian.cloudgame.events.GameStartEvent;
import com.simplyian.cloudgame.events.GameStopEvent;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import com.simplyian.cloudgame.gameplay.listeners.GameListener;
import com.simplyian.cloudgame.util.Messaging;
import java.util.List;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author ian
 */
public class LMSGameListener extends GameListener<HostedFFAState> {

    public LMSGameListener(MattLMS koth) {
        super(koth);
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        Game<HostedFFAState> game = game(event);
        if (game == null) {
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            getGameplay().sendBanner(p, "An LMS on map " + ChatColor.DARK_GREEN + game.getArena().getName() + " " + ChatColor.GREEN + "has started!",
                    "Type " + ChatColor.DARK_GREEN + "/lms spectate " + ChatColor.GREEN + "to spectate it!");
        }

        HostedFFAState state = game.getState();
        for (Player p : state.getPlayers()) {
            Location spawn = game.getArena().getNextSpawn();
            p.teleport(spawn);
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        }

        state.setStarted();
        (new LMSTimer(game)).runTimer();
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        Game<HostedFFAState> game = game(event);
        if (game == null) {
            return;
        }

        List<Player> players = game.getState().getPlayers();
        if (players.size() != 1) {
            game.broadcast("Game over! Nobody won!");
        } else {
            Player winner = players.get(0);
            game.broadcast(ChatColor.YELLOW + winner.getName() + ChatColor.RED + " has won the LMS!");
            getGameplay().sendGameMessage(winner, "To redeem your prize, type " + ChatColor.YELLOW + "/lms redeem" + ChatColor.RED + "!");
            ((MattLMS) getGameplay()).addPrize(winner);
        }

        game.stop();
    }

    @EventHandler
    public void onGameStop(GameStopEvent event) {
        Game<HostedFFAState> game = game(event);
        if (game == null) {
            return;
        }
        HostedFFAState state = game.getState();

        if (!state.isStarted()) {
            game.broadcast("The LMS has been cancelled.");
        } else {
            for (Player player : state.getSpectators()) {
                getGameplay().getPlugin().getPlayerStateManager().loadState(player);
            }

            for (Player player : state.getParticipants()) {
                Bukkit.getPluginManager().callEvent(new GameQuitEvent(game, player));
            }
        }

        state.setOver();
        getGameplay().getPlugin().getGameManager().removeGame(game);
        ((MattLMS) getGameplay()).setGame(null);
    }

}
