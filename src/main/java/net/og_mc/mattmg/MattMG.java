/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattmg;

import net.og_mc.mattkoth.KOTHCommand;
import org.bukkit.plugin.java.JavaPlugin;

import net.og_mc.mattkoth.MattKOTH;

import net.og_mc.mattlms.MattLMS;
import pw.ian.cloudgame.CloudGame;
import pw.ian.cloudgame.command.Commands;

/**
 *
 * @author not ian lol
 */
public class MattMG extends JavaPlugin {

    @Override
    public void onEnable() {
        CloudGame cloudGame = CloudGame.inst();
        cloudGame.addGameplay(new MattKOTH(this));
        cloudGame.addGameplay(new MattLMS(this));
    }

}
