/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import net.og_mc.mattmg.MattMG;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pw.ian.cloudgame.CloudGame;
import pw.ian.cloudgame.command.Commands;
import pw.ian.cloudgame.commands.hostedffa.FFACommand;
import pw.ian.cloudgame.gameplay.ColorScheme;
import pw.ian.cloudgame.gameplay.hostedffa.HostedFFA;
import pw.ian.cloudgame.gameplay.hostedffa.HostedFFAState;

/**
 *
 * @author ian
 */
public class MattLMS extends HostedFFA<HostedFFAState> {

    private final MattMG plugin;

    public MattLMS(MattMG plugin) {
        super(CloudGame.inst(), "LMS");
        this.plugin = plugin;
        setColorScheme(new ColorScheme(ChatColor.AQUA, ChatColor.DARK_AQUA, ChatColor.DARK_AQUA, ChatColor.AQUA, ChatColor.YELLOW));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        Commands.registerCommand(plugin, new FFACommand(this));
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
