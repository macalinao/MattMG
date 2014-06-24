/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplyian.cloudgame.commands.arena;

import com.simplyian.cloudgame.CloudGame;
import com.simplyian.cloudgame.command.TreeCommandHandler;
import org.bukkit.command.CommandSender;

/**
 *
 * @author ian
 */
public class ArenaCommand extends TreeCommandHandler {

    private final CloudGame plugin;

    public ArenaCommand(CloudGame plugin) {
        super("arena");
        this.plugin = plugin;
        setUsage("/arena <subcommand> [args]");
        setDescription("Allows managing arenas.");
    }

    @Override
    public void setupSubcommands() {
        addSubcommand("create", new ArenaCreateCommand(plugin));
        addSubcommand("delete", new ArenaDeleteCommand(plugin));
        addSubcommand("info", new ArenaInfoCommand(plugin));
        addSubcommand("listspawns", new ArenaListSpawnsCommand(plugin));
        addSubcommand("reload", new ArenaReloadCommand(plugin));
        addSubcommand("resetspawns", new ArenaResetSpawnsCommand(plugin));
        addSubcommand("setname", new ArenaSetNameCommand(plugin));
        addSubcommand("setspawn", new ArenaSetSpawnCommand(plugin));
    }

}
