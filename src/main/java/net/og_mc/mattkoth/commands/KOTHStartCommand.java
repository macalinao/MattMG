/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth.commands;

import com.simplyian.cloudgame.command.PlayerCommandHandler;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.model.arena.Arena;
import net.og_mc.mattkoth.KOTHState;
import net.og_mc.mattkoth.MattKOTH;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class KOTHStartCommand extends PlayerCommandHandler {

    private final MattKOTH koth;

    public KOTHStartCommand(MattKOTH koth) {
        super("start");
        this.koth = koth;
        setDescription("Starts a KOTH if there isn't already one going on.");
        setUsage("/koth start <arena>");
        setPermission("mattkoth.admin");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (!player.hasPermission("mattkoth.admin")) {
            koth.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (args.length == 0) {
            koth.sendGameMessage(player, "Usage: /koth start <arena> [mins = 10]");
            return;
        }

        Arena arena = koth.getPlugin().getModelManager().getArenas().find(player, args[0]);
        if (arena == null) {
            koth.sendGameMessage(player, "That arena does not exist.");
            return;
        }

        if (koth.getGame() != null) {
            koth.sendGameMessage(player, "A game has already been started.");
            return;
        }

        Game<KOTHState> game = koth.getPlugin().getGameManager().createGame(koth, arena);
        if (game == null) {
            koth.sendGameMessage(player, "KOTH is not supported on the given arena.");
            return;
        }

        int mins = 10;
        if (args.length >= 2) {
            try {
                mins = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                koth.sendGameMessage(player, "The number of minutes you specified isn't a valid number.");
                return;
            }
        }

        koth.setGame(game);
        game.getState().setHost(player);
        game.getState().setMins(mins);
        player.teleport(game.getArena().getNextSpawn());
        koth.sendGameMessage(player, "KOTH countdown started for a " + mins + " minute KOTH game.");
    }

}
