/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import com.simplyian.cloudgame.gameplay.hostedffa.HostedFFAState;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author ian
 */
public class KOTHState extends HostedFFAState {

    private UUID capturer;

    private long captureStart = -1;

    private ItemStack capturerHelmet;

    private Map<UUID, Integer> secondsCaptured = new HashMap<>();

    public Player getCapturer() {
        return Bukkit.getPlayer(capturer);
    }

    public void setCapturer(Player capturer) {
        // Store tracked capture seconds
        if (this.capturer != null) {
            if (secondsCaptured.containsKey(this.capturer)) {
                secondsCaptured.put(this.capturer, secondsCaptured.get(this.capturer) + lastSecs());
            } else {
                secondsCaptured.put(this.capturer, lastSecs());
            }
        }

        if (capturer == null) {
            this.capturer = null;
            this.captureStart = -1;
            return;
        }

        this.capturer = capturer.getUniqueId();
        this.captureStart = System.currentTimeMillis();
    }

    public int secondsCaptured() {
        if (capturer == null) {
            return -1;
        }
        Integer store = secondsCaptured.get(capturer);
        if (store == null) {
            secondsCaptured.put(capturer, 0);
            store = 0;
        }
        return store + lastSecs();
    }

    public int lastSecs() {
        return (int) ((System.currentTimeMillis() - captureStart) / 1000);
    }

    public boolean isCapturing() {
        return secondsCaptured() != -1;
    }

    public ItemStack getCapturerHelmet() {
        return capturerHelmet;
    }

    public void setCapturerHelmet(ItemStack capturerHelmet) {
        this.capturerHelmet = capturerHelmet;
    }
}
