package fr.nivcoo.supermission.superiorskyblock.permissions;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.PluginInitializeEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;

public class EssentialsFly implements Listener, CommandExecutor {

	private static IslandPrivilege ESSENTIALS_FLY;

	private String cancelMessage = "§7[§c§lES§7] Cette île ne permet pas de fly, votre fly est donc désactivé.";

	@EventHandler
	public void init(PluginInitializeEvent e) {
		IslandPrivilege.register("ESSENTIALS_FLY");
		ESSENTIALS_FLY = IslandPrivilege.getByName("ESSENTIALS_FLY");
	}

	private void setFly(Player p) {
		if (!p.getAllowFlight() || !p.isFlying() || p.getGameMode().equals(GameMode.CREATIVE))
			return;
		Island island = SuperiorSkyblockAPI.getIslandAt(p.getLocation());
		if (island == null)
			return;

		if (!island.hasPermission(p, ESSENTIALS_FLY)) {
			p.sendMessage(cancelMessage);
			p.setFlying(false);
			p.performCommand("fly");
		}
	}

	@EventHandler
	public void onMoveEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		setFly(p);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;

		Player p = (Player) sender;
		if (command.getName().contains("fly")) {

			Island island = SuperiorSkyblockAPI.getIslandAt(p.getLocation());
			if (island == null)
				return true;

			if (!island.hasPermission(p, ESSENTIALS_FLY)) {
				return false;
			}

		}
		return true;
	}

	@EventHandler
	public void preProcessCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (e.getMessage().equalsIgnoreCase("/fly") || e.getMessage().equalsIgnoreCase("/efly")
				|| e.getMessage().equalsIgnoreCase("/essentials:fly")
				|| e.getMessage().equalsIgnoreCase("/essentials:efly")) {

			Island island = SuperiorSkyblockAPI.getIslandAt(p.getLocation());
			if (island == null)
				return;
			if (!island.hasPermission(p, ESSENTIALS_FLY)) {
				p.sendMessage(cancelMessage);
				e.setCancelled(true);
			}

		}
	}

}
