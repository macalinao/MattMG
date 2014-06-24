/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth.commands;

import com.simplyian.cloudgame.command.TreeCommandHandler;
import com.simplyian.cloudgame.util.Messaging;
import net.og_mc.mattkoth.MattKOTH;
import net.og_mc.mattkoth.commands.KOTHForceStartCommand;
import net.og_mc.mattkoth.commands.KOTHJoinCommand;
import net.og_mc.mattkoth.commands.KOTHLeaveCommand;
import net.og_mc.mattkoth.commands.KOTHRedeemCommand;
import net.og_mc.mattkoth.commands.KOTHSetRegionCommand;
import net.og_mc.mattkoth.commands.KOTHSetSpawnCommand;
import net.og_mc.mattkoth.commands.KOTHSpectateCommand;
import net.og_mc.mattkoth.commands.KOTHStartCommand;
import net.og_mc.mattkoth.commands.KOTHStopCommand;
import org.bukkit.entity.Player;

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
