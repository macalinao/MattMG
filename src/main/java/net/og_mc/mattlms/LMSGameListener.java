/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattlms;

import com.simplyian.cloudgame.events.GameStartEvent;
import com.simplyian.cloudgame.game.Game;
import com.simplyian.cloudgame.gameplay.GameListener;
import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import net.og_mc.mattmg.Kits;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author ian
 */
public class LMSGameListener extends GameListener<HostedFFAState> {

    public LMSGameListener(MattLMS koth) {
        super(koth);
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        Game<HostedFFAState> game = game(event);
        if (game == null) {
            return;
        }
        HostedFFAState state = game.getState();
        for (Player p : state.getPlayers()) {
            if (state.isEasy()) {
                Kits.loadEasyKit(p);
            }
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void postGameStart(GameStartEvent event) {
        Game<HostedFFAState> game = game(event);
        if (game == null) {
            return;
        }
        (new LMSTimer(game)).runTimer();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Game<HostedFFAState> game = game((Player) event.getWhoClicked());
        if (game == null) {
            return;
        }

        if (event.getSlot() == 103) { // helmet
            event.setCancelled(true);
        }
    }
}
