/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import com.simplyian.cloudgame.CloudGame;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFA;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import com.simplyian.cloudgame.model.arena.Arena;

/**
 *
 * @author ian
 */
public class MattLMS extends HostedFFA<HostedFFAState> {

    public MattLMS(CloudGame plugin) {
        super(plugin, "LMS");
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getPlugin().getCommands().registerCommand("lms", new LMSCommand(this));

        getPlugin().getServer().getPluginManager().registerEvents(new LMSGameListener(this), getPlugin());
    }

    @Override
    public boolean canUse(Arena arena) {
        return !arena.getSpawns().isEmpty();
    }

    @Override
    public void setup(Game<HostedFFAState> g) {
        (new LMSAnnouncerTask(g)).runTaskTimer(getPlugin(), 2L, 2L);
    }
}
