/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import com.simplyian.cloudgame.commands.hostedffa.FFACommand;
import net.og_mc.mattkoth.MattKOTH;

/**
 * The main command for all that shizzzzle
 *
 * @author ian
 */
public class KOTHCommand extends FFACommand {

    private final MattKOTH koth;

    public KOTHCommand(MattKOTH koth) {
        super(koth);
        this.koth = koth;
        setColorScheme(koth.getColorScheme());
    }

    @Override
    public void setupSubcommands() {
        super.setupSubcommands();
        addSubcommand("setregion", new KOTHSetRegionCommand(koth));
    }
}
