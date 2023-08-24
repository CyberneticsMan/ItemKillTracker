package me.cyberneticsman.itemkilltracker;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KillTrackerCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
		if (commandSender instanceof Player player) {
			ItemStack stack = player.getInventory().getItemInMainHand();
			if (!stack.getType().isAir()){
				NBTItem itemNBT = new NBTItem(stack);
				itemNBT.setInteger("kills", 0);
				itemNBT.setBoolean("kills_tracked", true);
				itemNBT.applyNBT(stack);
				ItemMeta meta = stack.getItemMeta();
				if (meta!=null){
					List<Component> components = new ArrayList<>();
					components.add(0, Component.text("Players Killed: " + itemNBT.getInteger("kills")));
					meta.lore(components);
					stack.setItemMeta(meta);
				}
			}

		}
		// If the player (or console) uses our command correct, we can return true
		return true;
	}
}
