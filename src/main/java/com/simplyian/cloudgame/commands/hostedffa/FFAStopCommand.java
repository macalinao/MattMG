/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplyian.cloudgame.commands.hostedffa;

import com.simplyian.cloudgame.command.PlayerCommandHandler;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFA;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class FFAStopCommand extends PlayerCommandHandler {

    private final HostedFFA ffa;

    public FFAStopCommand(HostedFFA ffa) {
        super("stop");
        this.ffa = ffa;
        setDescription("Stops the " + ffa.getName() + " in progress.");
        setUsage("/" + ffa.getId() + " stop");
        setPermission("mattgame.admin");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (!player.hasPermission("mattmg.admin")) {
            ffa.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (ffa.getGame() == null) {
            ffa.sendGameMessage(player, "There isn't a game going on right now.");
            return;
        }

        ffa.getGame().stop();
        ffa.sendGameMessage(player, "Game stopped.");
    }

}
