/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattkoth;

import com.simplyian.cloudgame.gameplay.states.FFAState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static net.og_mc.mattkoth.KOTHConstants.CAPTURE_WIN_SECONDS;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author ian
 */
public class KOTHState extends FFAState {

    private UUID host;

    private long start = -1;

    private UUID capturer;

    private long captureStart = -1;

    private ItemStack capturerHelmet;

    private boolean over = false;

    private Map<UUID, Integer> secondsCaptured = new HashMap<>();

    public Player getHost() {
        return Bukkit.getPlayer(host);
    }

    public void setHost(Player player) {
        host = player.getUniqueId();
    }

    public boolean isStarted() {
        return start != -1;
    }

    public void setStarted() {
        this.start = System.currentTimeMillis();
    }

    public long getStart() {
        return start;
    }

    public Player getCapturer() {
        return Bukkit.getPlayer(capturer);
    }

    public void setCapturer(Player capturer) {
        // Store tracked capture seconds
        if (this.capturer != null) {
            if (secondsCaptured.containsKey(this.capturer)) {
                secondsCaptured.put(this.capturer, lastSecs());
            } else {
                secondsCaptured.put(this.capturer, secondsCaptured.get(host) + lastSecs());
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

    public boolean isOvertime() {
        return secondsCaptured() != -1 && secondsCaptured() >= CAPTURE_WIN_SECONDS;
    }

    public int remainingTime() {
        int secsElapsed = (((int) (System.currentTimeMillis() - start)) / 1000);
        return (10 * 60) - secsElapsed;
    }

    public ItemStack getCapturerHelmet() {
        return capturerHelmet;
    }

    public void setCapturerHelmet(ItemStack capturerHelmet) {
        this.capturerHelmet = capturerHelmet;
    }

    @Override
    public List<Player> getParticipants() {
        List<Player> participants = new ArrayList<>();
        participants.addAll(getSpectators());
        participants.addAll(getPlayers());
        Player host = getHost();
        if (host != null) {
            participants.add(host);
        }
        return participants;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver() {
        this.over = true;
    }

}
