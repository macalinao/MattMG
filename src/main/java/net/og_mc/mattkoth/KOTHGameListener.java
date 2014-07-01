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
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import net.og_mc.mattmg.Kits;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

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
            getGameplay().sendBanner(p, "A KOTH on map $D" + game.getArena().getName() + " $Lhas started!",
                    "Type $D/koth spectate $Lto spectate it!");
        }

        KOTHState state = game.getState();
        for (Player p : state.getPlayers()) {
            Location spawn = game.getArena().getNextSpawn();
            if (state.isEasy()) {
                getGameplay().getPlugin().getPlayerStateManager().saveState(p);
                Kits.loadEasyKit(p);
            }
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

        Player winner = event.getWinner();
        if (winner == null) {
            game.broadcast("Game over! Nobody won!");
        } else {
            game.broadcast("$H" + winner.getName() + "$M has won the KOTH!");
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

    @EventHandler
    public void onGameQuit(GameQuitEvent e) {
        Game<KOTHState> game = game(e);
        if (game == null) {
            return;
        }

        if (game.getState().isEasy()) {
            getGameplay().getPlugin().getPlayerStateManager().loadState(e.getPlayer());
        }
    }

}
