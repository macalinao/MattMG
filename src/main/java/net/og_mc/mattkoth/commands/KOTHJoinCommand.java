/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth.commands;

import com.simplyian.cloudgame.command.PlayerCommandHandler;
import com.simplyian.cloudgame.events.GameJoinEvent;
import net.og_mc.mattkoth.MattKOTH;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class KOTHJoinCommand extends PlayerCommandHandler {

    private final MattKOTH koth;

    public KOTHJoinCommand(MattKOTH koth) {
        super("join");
        this.koth = koth;
        setDescription("Joins the KOTH.");
        setUsage("/koth join");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (koth.getGame() == null) {
            koth.sendGameMessage(player, "There isn't a game going on right now.");
            return;
        }
        Bukkit.getPluginManager().callEvent(new GameJoinEvent(koth.getGame(), player));
    }

}
