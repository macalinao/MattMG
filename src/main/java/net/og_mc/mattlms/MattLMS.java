/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import com.simplyian.cloudgame.CloudGame;
import com.simplyian.cloudgame.commands.hostedffa.FFACommand;
import com.simplyian.cloudgame.gameplay.ColorScheme;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFA;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class MattLMS extends HostedFFA<HostedFFAState> {

    public MattLMS(CloudGame plugin) {
        super(plugin, "LMS");
        setColorScheme(new ColorScheme(ChatColor.AQUA, ChatColor.DARK_AQUA, ChatColor.DARK_AQUA, ChatColor.AQUA, ChatColor.YELLOW));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getPlugin().getCommands().registerCommand("lms", new FFACommand(this));

        getPlugin().getServer().getPluginManager().registerEvents(new LMSListener(this), getPlugin());
    }

    @Override
    public void sendGameMessage(Player p, String message) {
        p.sendMessage("[" + ChatColor.DARK_AQUA + "LMS" + ChatColor.WHITE + "] " + ChatColor.AQUA + getColorScheme().replaceColors(message));
    }

    @Override
    public HostedFFAState newState() {
        HostedFFAState state = super.newState();
        state.setProvideArmor(true);
        return state;
    }
}
