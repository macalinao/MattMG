/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import com.simplyian.cloudgame.commands.hostedffa.FFACommand;

/**
 *
 * @author ian
 */
public class LMSCommand extends FFACommand {

    private final MattLMS lms;

    public LMSCommand(MattLMS lms) {
        super(lms);
        this.lms = lms;
    }

    @Override
    public void setupSubcommands() {
        super.setupSubcommands();
        addSubcommand("setregion", new LMSSetRegionCommand(lms));
    }

}
