/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import com.simplyian.cloudgame.command.PlayerCommandHandler;
import com.simplyian.cloudgame.model.arena.Arena;
import com.simplyian.cloudgame.model.region.Region;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author ian
 */
public class LMSSetRegionCommand extends PlayerCommandHandler {

    private final MattLMS lms;

    public LMSSetRegionCommand(MattLMS lms) {
        super("setregion");
        this.lms = lms;
        setDescription("Sets an LMS arena.");
        setUsage("/" + lms.getId() + " setregion <arena>");
        setPermission("mattmg.admin");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (!player.hasPermission("mattmg.admin")) {
            lms.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (args.length == 0) {
            sendUsageMessage(player);
            return;
        }

        Arena arena = lms.getPlugin().getModelManager().getArenas().find(player, args[0]);
        if (arena == null) {
            Region arenaRegion = null;
            if (args[0].contains(";")) {
                arenaRegion = lms.getPlugin().getModelManager().getRegions().findById(args[0]);
            } else {
                arenaRegion = lms.getPlugin().getModelManager().getRegions().find(player.getWorld(), args[0]);
            }

            if (arenaRegion == null) {
                lms.sendGameMessage(player, "Invalid arena region.");
                return;
            }

            arena = lms.getPlugin().getModelManager().getArenas().create(arenaRegion);
            arena.setName(arenaRegion.getRegion().getId());
            lms.sendGameMessage(player, "The arena did not exist, so one was created on that region.");
        }

        lms.sendGameMessage(player, "The arena at '" + ChatColor.YELLOW + arena.getId() + "' has been created.");
    }

}
