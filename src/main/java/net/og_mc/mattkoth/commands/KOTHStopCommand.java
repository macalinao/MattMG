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
public class KOTHStopCommand extends PlayerCommandHandler {

    private final MattKOTH koth;

    public KOTHStopCommand(MattKOTH koth) {
        super("stop");
        this.koth = koth;
        setDescription("Stops the KOTH in progress.");
        setUsage("/koth stop");
    }

    @Override
    public void onCommand(Player player, String[] args) {
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

}
