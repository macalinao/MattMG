/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplyian.cloudgame.commands.hostedffa;

import com.simplyian.cloudgame.command.PlayerCommandHandler;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFA;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import com.simplyian.cloudgame.model.arena.Arena;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class FFAStartCommand extends PlayerCommandHandler {

    private static final int TWELVE_YEARS_A_GAME = 24 * 60 * 365 * 12;

    private final HostedFFA ffa;

    public FFAStartCommand(HostedFFA ffa) {
        super("start");
        this.ffa = ffa;
        setDescription("Starts a " + ffa.getName() + " if there isn't already one going on.");
        setUsage("/" + ffa.getId() + " start <arena> [mins]");
        setPermission("mattmg.admin");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (!player.hasPermission("mattmg.admin")) {
            ffa.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (args.length == 0) {
            sendUsageMessage(player);
            return;
        }

        Arena arena = ffa.getPlugin().getModelManager().getArenas().find(player, args[0]);
        if (arena == null) {
            ffa.sendGameMessage(player, "That arena does not exist.");
            return;
        }

        if (ffa.getGame() != null) {
            ffa.sendGameMessage(player, "A game has already been started.");
            return;
        }

        Game<HostedFFAState> game = ffa.getPlugin().getGameManager().createGame(ffa, arena);
        if (game == null) {
            ffa.sendGameMessage(player, ffa.getName() + " is not supported on the given arena.");
            return;
        }

        int mins = TWELVE_YEARS_A_GAME; // Game is default 1 year, which for our purposes is unlimited
        if (args.length >= 2) {
            try {
                mins = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                ffa.sendGameMessage(player, "The number of minutes you specified isn't a valid number.");
                return;
            }
        }

        boolean easy = false;
        if (args.length >= 3) {
            easy = args[2].equalsIgnoreCase("easy");
        }

        ffa.setGame(game);
        game.getState().setHost(player);
        game.getState().setMins(mins);
        player.teleport(game.getArena().getNextSpawn());
        if (mins == TWELVE_YEARS_A_GAME) {
            ffa.sendGameMessage(player, ffa.getName() + " countdown started for an infinite time " + ffa.getName() + " game.");
        } else {
            ffa.sendGameMessage(player, ffa.getName() + " countdown started for a " + mins + " minute " + ffa.getName() + " game.");
        }
    }

}
