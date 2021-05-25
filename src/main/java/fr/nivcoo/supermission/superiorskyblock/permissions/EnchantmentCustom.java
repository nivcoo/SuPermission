package fr.nivcoo.supermission.superiorskyblock.permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.PluginInitializeEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;

import net.advancedplugins.ae.api.EffectActivationEvent;


public class EnchantmentCustom implements Listener {
	
	private static IslandPrivilege ENCHANTMENT_CUSTOM;
	
	private String cancelMessage = "§7[§c§lES§7] Cette île ne permet pas d'utiliser les enchants custom.";
	
	@EventHandler
	public void init(PluginInitializeEvent e) {
		IslandPrivilege.register("ENCHANTMENT_CUSTOM");
		ENCHANTMENT_CUSTOM = IslandPrivilege.getByName("ENCHANTMENT_CUSTOM");
	}

	@EventHandler
	public void onEffectActivationEvent(EffectActivationEvent e) {
		
		if(!(e.getMainEntity() instanceof Player) || e.isRemoval())
			return;
		Player p = (Player) e.getMainEntity();
		
		Island island = SuperiorSkyblockAPI.getIslandAt(p.getLocation());
		if (island == null)
			return;
		if (!island.hasPermission(p, ENCHANTMENT_CUSTOM)) {
			p.sendMessage(cancelMessage);
			e.setCancelled(true);
		}
	}
}
