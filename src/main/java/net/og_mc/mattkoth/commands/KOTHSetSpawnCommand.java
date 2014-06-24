/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth.commands;

import com.simplyian.cloudgame.command.PlayerCommandHandler;
import com.simplyian.cloudgame.events.GameJoinEvent;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.model.arena.Arena;
import net.og_mc.mattkoth.KOTHState;
import net.og_mc.mattkoth.MattKOTH;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class KOTHSetSpawnCommand extends PlayerCommandHandler {

    private final MattKOTH koth;

    public KOTHSetSpawnCommand(MattKOTH koth) {
        super("setspawn");
        this.koth = koth;
        setDescription("Sets a spawn on the KOTH map.");
        setUsage("/koth setspawn <hill region> <main region>");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (!player.hasPermission("mattkoth.admin")) {
            koth.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (args.length <= 1) {
            sendUsageMessage(player);
            return;
        }

        Arena arena = koth.getPlugin().getModelManager().getArenas().find(player, args[0]);
        if (arena == null) {
            koth.sendGameMessage(player, "That arena does not exist.");
            return;
        }

        int spawnNumber = 0;
        try {
            spawnNumber = Integer.parseInt(args[1]);
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

}
