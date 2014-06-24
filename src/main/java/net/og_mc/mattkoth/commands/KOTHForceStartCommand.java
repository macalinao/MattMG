/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth.commands;

import com.simplyian.cloudgame.command.PlayerCommandHandler;
import com.simplyian.cloudgame.events.GameStartEvent;
import net.og_mc.mattkoth.MattKOTH;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class KOTHForceStartCommand extends PlayerCommandHandler {

    private final MattKOTH koth;

    public KOTHForceStartCommand(MattKOTH koth) {
        super("forcestart");
        this.koth = koth;
        setDescription("Bypasses the KOTH countdown.");
        setUsage("/koth forcestart");
        setPermission("mattkoth.admin");
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

        Bukkit.getPluginManager().callEvent(new GameStartEvent(koth.getGame()));
    }

}
