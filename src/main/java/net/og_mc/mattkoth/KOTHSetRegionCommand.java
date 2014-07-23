/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pw.ian.cloudgame.command.PlayerCommandHandler;
import pw.ian.cloudgame.model.arena.Arena;
import pw.ian.cloudgame.model.region.Region;

/**
 *
 * @author ian
 */
public class KOTHSetRegionCommand extends PlayerCommandHandler {

    private final MattKOTH koth;

    public KOTHSetRegionCommand(MattKOTH koth) {
        super("setregion");
        this.koth = koth;
        setDescription("Sets the KOTH region.");
        setUsage("/koth setregion <region> <arena>");
        setPermission("mattmg.admin");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (!player.hasPermission("mattmg.admin")) {
            koth.sendGameMessage(player, "You can't use this command.");
            return;
        }

        if (args.length <= 1) {
            sendUsageMessage(player);
            return;
        }

        Region region = null;
        if (args[0].contains(";")) {
            region = koth.getPlugin().getModelManager().getRegions().findById(args[0]);
        } else {
            region = koth.getPlugin().getModelManager().getRegions().find(player.getWorld(), args[0]);
        }

        if (region == null) {
            koth.sendGameMessage(player, "Invalid region.");
            return;
        }

        Arena arena = koth.getPlugin().getModelManager().getArenas().find(player, args[1]);
        if (arena == null) {
            Region arenaRegion = null;
            if (args[1].contains(";")) {
                arenaRegion = koth.getPlugin().getModelManager().getRegions().findById(args[1]);
            } else {
                arenaRegion = koth.getPlugin().getModelManager().getRegions().find(player.getWorld(), args[1]);
            }

            if (arenaRegion == null) {
                koth.sendGameMessage(player, "Invalid arena region.");
                return;
            }

            arena = koth.getPlugin().getModelManager().getArenas().create(arenaRegion);
            arena.setName(arenaRegion.getRegion().getId());
            koth.sendGameMessage(player, "The arena did not exist, so one was created on that region.");
        }

        arena.setProperty("koth-hill", region.getId());
        koth.sendGameMessage(player, "The hill of arena " + ChatColor.YELLOW + arena.getId()
                + ChatColor.GREEN + " has been set to " + ChatColor.YELLOW + region.getId() + ChatColor.GREEN + ".");
    }

}
