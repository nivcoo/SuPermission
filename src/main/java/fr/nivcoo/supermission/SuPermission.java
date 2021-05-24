package fr.nivcoo.supermission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.nivcoo.supermission.superiorskyblock.permissions.EnchantmentCustom;
import fr.nivcoo.supermission.superiorskyblock.permissions.EssentialsFly;

public class SuPermission extends JavaPlugin {

	private static SuPermission INSTANCE;

	@Override
	public void onEnable() {
		INSTANCE = this;
		Bukkit.getPluginManager().registerEvents(new EssentialsFly(), this);
		Bukkit.getPluginManager().registerEvents(new EnchantmentCustom(), this);
	}

	@Override
	public void onDisable() {
	}

	public static SuPermission get() {
		return INSTANCE;
	}

}
