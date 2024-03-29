/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.og_mc.mattmg;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/**
 *
 * @author ian
 */
public class Kits {

    public static void loadEasyKit(Player p) {
        PlayerInventory inv = p.getInventory();

        inv.setHelmet(new ItemStack(Material.IRON_HELMET));
        inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS));

        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
        diamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        inv.setItem(0, diamondSword);

        Potion heal = new Potion(PotionType.INSTANT_HEAL);
        heal.setSplash(true);
        heal.setLevel(1);
        inv.setItem(5, heal.toItemStack(10));

        Potion speed = new Potion(PotionType.SPEED);
        speed.setLevel(1);
        inv.setItem(6, speed.toItemStack(2));

        inv.setItem(7, new ItemStack(Material.GOLDEN_APPLE, 4));
        inv.setItem(8, new ItemStack(Material.GRILLED_PORK, 32));
        inv.setHeldItemSlot(0);
    }

}
