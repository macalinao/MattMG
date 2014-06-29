/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import com.simplyian.cloudgame.CloudGame;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.Gameplay;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFA;
import com.simplyian.cloudgame.model.arena.Arena;
import com.simplyian.cloudgame.model.region.Region;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.og_mc.mattkoth.commands.KOTHCommand;
import net.og_mc.mattkoth.listeners.KOTHCaptureListener;
import net.og_mc.mattkoth.listeners.KOTHCommandListener;
import net.og_mc.mattkoth.listeners.KOTHDeathListener;
import net.og_mc.mattkoth.listeners.KOTHGameListener;
import net.og_mc.mattkoth.listeners.KOTHGamePlayerListener;
import net.og_mc.mattkoth.tasks.KOTHAnnouncerTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class MattKOTH extends HostedFFA<KOTHState> {

    public MattKOTH(CloudGame plugin) {
        super(plugin, "KOTH");
    }

    @Override
    public void onEnable() {
        getPlugin().getCommands().registerCommand("koth", new KOTHCommand(this));

        getPlugin().getServer().getPluginManager().registerEvents(new KOTHCaptureListener(this), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new KOTHCommandListener(this), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new KOTHGameListener(this), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new KOTHGamePlayerListener(this), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new KOTHDeathListener(this), getPlugin());
    }

    @Override
    public boolean canUse(Arena arena) {
        if (arena.getSpawns().isEmpty()) {
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
    public void setup(Game<KOTHState> g) {
        (new KOTHAnnouncerTask(g)).runTaskTimer(getPlugin(), 2L, 2L);
    }
}
