/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import org.bukkit.ChatColor;
import pw.ian.cloudgame.CloudGame;
import pw.ian.cloudgame.gameplay.ColorScheme;
import pw.ian.cloudgame.gameplay.hostedffa.HostedFFA;
import pw.ian.cloudgame.model.arena.Arena;
import pw.ian.cloudgame.model.region.Region;

/**
 *
 * @author ian
 */
public class MattKOTH extends HostedFFA<KOTHState> {

    public static final int CAPTURE_WIN_SECONDS = 120;

    public MattKOTH(CloudGame plugin) {
        super(plugin, "KOTH");
        setColorScheme(new ColorScheme(ChatColor.GREEN, ChatColor.DARK_GREEN, ChatColor.DARK_RED, ChatColor.RED, ChatColor.YELLOW));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getPlugin().getCommands().registerCommand("koth", new KOTHCommand(this));

        getPlugin().getServer().getPluginManager().registerEvents(new KOTHCaptureListener(this), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new KOTHGameListener(this), getPlugin());
    }

    @Override
    public boolean canUse(Arena arena) {
        if (!super.canUse(arena)) {
            return false;
        }
        if (!arena.hasProperty("koth-hill")) {
            return false;
        }
        String hillRegion = arena.getProperty("koth-hill").toString();
        Region region = getPlugin().getModelManager().getRegions().findById(hillRegion);
        if (region == null) {
            arena.removeProperty("koth-hill");
        }
        return region != null;
    }

    @Override
    public KOTHState newState() {
        KOTHState state = super.newState();
        state.setProvideArmor(false);
        return state;
    }
}
