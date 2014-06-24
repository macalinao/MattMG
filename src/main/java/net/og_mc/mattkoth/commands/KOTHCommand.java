/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth.commands;

import com.simplyian.cloudgame.command.TreeCommandHandler;
import net.og_mc.mattkoth.MattKOTH;

/**
 * The main command for all that shizzzzle
 *
 * @author ian
 */
public class KOTHCommand extends TreeCommandHandler {

    private final MattKOTH koth;

    public KOTHCommand(MattKOTH koth) {
        super("koth");
        this.koth = koth;
    }

    @Override
    public void setupSubcommands() {
        addSubcommand("forcestart", new KOTHForceStartCommand(koth));
        addSubcommand("join", new KOTHJoinCommand(koth));
        addSubcommand("leave", new KOTHLeaveCommand(koth));
        addSubcommand("redeem", new KOTHRedeemCommand(koth));
        addSubcommand("setregion", new KOTHSetRegionCommand(koth));
        addSubcommand("setspawn", new KOTHSetSpawnCommand(koth));
        addSubcommand("spectate", new KOTHSpectateCommand(koth));
        addSubcommand("start", new KOTHStartCommand(koth));
        addSubcommand("stop", new KOTHStopCommand(koth));
    }
}
