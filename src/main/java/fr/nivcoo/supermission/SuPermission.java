package fr.nivcoo.supermission;

import fr.nivcoo.supermission.settings.EntitiesStacking;
import fr.nivcoo.supermission.superiorskyblock.permissions.EssentialsFly;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SuPermission extends JavaPlugin {

    private static SuPermission INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new EssentialsFly(), this);
        Bukkit.getPluginManager().registerEvents(new EntitiesStacking(), this);
    }

    @Override
    public void onDisable() {
    }

    public static SuPermission get() {
        return INSTANCE;
    }

}
