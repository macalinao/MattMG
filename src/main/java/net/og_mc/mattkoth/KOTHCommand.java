/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import com.simplyian.cloudgame.command.PlayerCommandHandler;
import com.simplyian.cloudgame.events.GameJoinEvent;
import com.simplyian.cloudgame.events.GameLeaveEvent;
import com.simplyian.cloudgame.events.GameSpectateEvent;
import com.simplyian.cloudgame.events.GameStartEvent;
import com.simplyian.cloudgame.events.GameUnspectateEvent;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.model.arena.Arena;
import com.simplyian.cloudgame.model.region.Region;
import com.simplyian.cloudgame.util.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * The main command for all that shizzzzle
 *
 * @author ian
 */
public class KOTHCommand extends PlayerCommandHandler {

    private final MattKOTH koth;

    public KOTHCommand(MattKOTH koth) {
        this.koth = koth;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            noArgs(player);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "join":
                join(player, args);
                return;
            case "leave":
                leave(player, args);
                return;
            case "spectate":
                spectate(player, args);
                return;
            case "redeem":
                redeem(player, args);
                return;

            case "start":
                start(player, args);
                return;
            case "stop":
                stop(player, args);
                return;
            case "forcestart":
                forcestart(player, args);
                return;
            case "setregion":
                setregion(player, args);
                return;
            case "setspawn":
                setspawn(player, args);
                return;
        }

        noArgs(player);
    }

    private void noArgs(Player player) {
        Messaging.sendBanner(player,
                "/koth join - Join the KOTH",
                "/koth leave - Leaves the KOTH",
                "/koth spectate - Spectates the KOTH",
                "/koth redeem - Redeems a KOTH prize");
        if (player.hasPermission("mattkoth.admin")) {
            Messaging.sendBanner(player,
                    "/koth start - Starts a KOTH if there isn't already one going on",
                    "/koth stop - Stops a KOTH in progress",
                    "/koth forcestart - Bypasses the KOTH countdown",
                    "/koth setregion - Set the KOTH region",
                    "/koth setspawn - Sets a spawn on the koth map");
        }
    }

    private void join(Player player, String[] args) {
        if (koth.getGame() == null) {
            koth.sendGameMessage(player, "There isn't a game going on right now.");
            return;
        }
        Bukkit.getPluginManager().callEvent(new GameJoinEvent(koth.getGame(), player));
    }

    private void leave(Player player, String[] args) {
        if (koth.getGame() == null) {
            koth.sendGameMessage(player, "There isn't a game going on right now.");
            return;
        }
        Bukkit.getPluginManager().callEvent(new GameLeaveEvent(koth.getGame(), player));
    }

    private void spectate(Player player, String[] args) {
        if (koth.getGame() == null) {
            koth.sendGameMessage(player, "There is currently no game to spectate.");
            return;
        }

        if (koth.getGame().getState().hasSpectator(player)) {
            Bukkit.getPluginManager().callEvent(new GameUnspectateEvent(koth.getGame(), player));
        } else {
            Bukkit.getPluginManager().callEvent(new GameSpectateEvent(koth.getGame(), player));
        }
    }

    private void start(Player player, String[] args) {
        if (!player.hasPermission("mattkoth.admin")) {
            koth.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (args.length <= 1) {
            koth.sendGameMessage(player, "Usage: /koth start <arena>");
            return;
        }

        Arena arena = koth.getPlugin().getModelManager().getArenas().find(player, args[1]);
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

        koth.setGame(game);
        game.getState().setHost(player);
        player.teleport(game.getArena().getNextSpawn());
        koth.sendGameMessage(player, "KOTH countdown started.");
    }

    private void stop(Player player, String[] args) {
        if (!player.hasPermission("mattkoth.admin")) {
            koth.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (koth.getGame() == null) {
            koth.sendGameMessage(player, "There isn't a game going on right now.");
            return;
        }

        koth.getGame().stop();
        koth.sendGameMessage(player, "Game stopped.");
    }

    private void forcestart(Player player, String[] args) {
        if (!player.hasPermission("mattkoth.admin")) {
            koth.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (koth.getGame() == null) {
            koth.sendGameMessage(player, "There isn't a game going on right now.");
            return;
        }

        Bukkit.getPluginManager().callEvent(new GameStartEvent(koth.getGame()));
    }

    private void setregion(Player player, String[] args) {
        if (!player.hasPermission("mattkoth.admin")) {
            koth.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (args.length <= 2) {
            koth.sendGameMessage(player, "Usage: /koth setregion <region> <arena>");
            return;
        }

        Region region = null;
        if (args[1].contains(";")) {
            region = koth.getPlugin().getModelManager().getRegions().findById(args[1]);
        } else {
            region = koth.getPlugin().getModelManager().getRegions().find(player.getWorld(), args[1]);
        }

        if (region == null) {
            koth.sendGameMessage(player, "Invalid region.");
            return;
        }

        Arena arena = koth.getPlugin().getModelManager().getArenas().find(player, args[2]);
        if (arena == null) {
            Region arenaRegion = null;
            if (args[2].contains(";")) {
                arenaRegion = koth.getPlugin().getModelManager().getRegions().findById(args[2]);
            } else {
                arenaRegion = koth.getPlugin().getModelManager().getRegions().find(player.getWorld(), args[2]);
            }

            if (arenaRegion == null) {
                koth.sendGameMessage(player, "Invalid arena region.");
                return;
            }

            arena = koth.getPlugin().getModelManager().getArenas().create(arenaRegion);
            arena.setName(arenaRegion.getRegion().getId());
            koth.sendGameMessage(player, "The arena did not exist, so one was created on that region.");
        }

        arena.setProperty("koth-hill", region.getId());
        koth.sendGameMessage(player, "The hill of arena " + ChatColor.YELLOW + arena.getId()
                + ChatColor.GREEN + " has been set to " + ChatColor.YELLOW + region.getId() + ChatColor.GREEN + ".");
    }

    private void setspawn(Player player, String[] args) {
        if (!player.hasPermission("mattkoth.admin")) {
            koth.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (args.length <= 2) {
            koth.sendGameMessage(player, "Usage: /koth setspawn <arena> <spawn number>");
            return;
        }

        Arena arena = koth.getPlugin().getModelManager().getArenas().find(player, args[1]);
        if (arena == null) {
            koth.sendGameMessage(player, "That arena does not exist.");
            return;
        }

        int spawnNumber = 0;
        try {
            spawnNumber = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            koth.sendGameMessage(player, "That spawn id is an invalid number.");
            return;
        }

        if (spawnNumber < 1 || spawnNumber > 5) {
            koth.sendGameMessage(player, "You can only set spawns 1 through 5.");
            return;
        }

        arena.setSpawn(spawnNumber - 1, player.getLocation());
        koth.sendGameMessage(player, "Spawn " + spawnNumber + " has been set.");
    }

    private void redeem(Player player, String[] args) {
        if (koth.getGame() != null && koth.getGame().getState().hasPlayer(player)) {
            koth.sendGameMessage(player, "You can't use this command in game!");
            return;
        }

        if (!koth.redeemPrize(player)) {
            koth.sendGameMessage(player, "You don't have a prize to redeem!");
            return;
        }

        koth.sendGameMessage(player, "Enjoy your prize!");
    }
}
