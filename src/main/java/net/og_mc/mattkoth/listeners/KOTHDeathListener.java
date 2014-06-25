/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth.listeners;

import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.listeners.GameListener;
import me.confuser.barapi.BarAPI;
import net.og_mc.mattkoth.KOTHState;
import net.og_mc.mattkoth.MattKOTH;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author ian
 */
public class KOTHDeathListener extends GameListener<KOTHState> {

    public KOTHDeathListener(MattKOTH koth) {
        super(koth);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Game<KOTHState> game = game(p);
        if (game == null || !game.getState().isStarted()) {
            return;
        }

        game.getState().removePlayer(p);
        BarAPI.removeBar(p);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + p.getName());
    }
}
