/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattmg;

import net.og_mc.mattkoth.MattKOTH;
import com.simplyian.cloudgame.CloudGame;
import net.og_mc.mattlms.MattLMS;

/**
 *
 * @author ian
 */
public class MattMG extends CloudGame {

    @Override
    public void addGameplays() {
        addGameplay(new MattKOTH(this));
        addGameplay(new MattLMS(this));
    }

}
