package me.cyberneticsman.itemkilltracker;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ItemKillTracker extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(this.getCommand("kill-tracker")).setExecutor(new KillTrackerCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event){
        Player killedPlayer = event.getPlayer();

        if (killedPlayer.getKiller() != null){
            Player killer = killedPlayer.getKiller();

            ItemStack stack = killer.getInventory().getItemInMainHand();
            if (!stack.getType().isAir()){
                NBTItem itemNBT = new NBTItem(stack);
                if (itemNBT.getBoolean("kills_tracked")) {
                    itemNBT.setInteger("kills", itemNBT.getInteger("kills") + 1);
                    itemNBT.applyNBT(stack);
                    ItemMeta meta = stack.getItemMeta();
                    if (meta != null) {
                        System.out.println(itemNBT.getInteger("kills"));
                        List<Component> components = new ArrayList<>();
                        components.add(0, Component.text("Players Killed: " + itemNBT.getInteger("kills")));
                        meta.lore(components);
                        stack.setItemMeta(meta);
                    }
                }
            }
        }

    }
}
