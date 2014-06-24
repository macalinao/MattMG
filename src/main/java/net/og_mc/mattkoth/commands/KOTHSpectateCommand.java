/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth.commands;

import com.simplyian.cloudgame.command.PlayerCommandHandler;
import com.simplyian.cloudgame.events.GameSpectateEvent;
import com.simplyian.cloudgame.events.GameUnspectateEvent;
import net.og_mc.mattkoth.MattKOTH;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class KOTHSpectateCommand extends PlayerCommandHandler {

    private final MattKOTH koth;

    public KOTHSpectateCommand(MattKOTH koth) {
        super("spectate");
        this.koth = koth;
        setDescription("Spectates the KOTH.");
        setUsage("/koth spectate");
    }

    @Override
    public void onCommand(Player player, String[] args) {
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

}
