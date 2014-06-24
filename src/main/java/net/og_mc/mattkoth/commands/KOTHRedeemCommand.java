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
public class KOTHRedeemCommand extends PlayerCommandHandler {

    private final MattKOTH koth;

    public KOTHRedeemCommand(MattKOTH koth) {
        super("redeem");
        this.koth = koth;
        setDescription("Redeems a KOTH prize.");
        setUsage("/koth redeem");
    }

    @Override
    public void onCommand(Player player, String[] args) {
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
