package fr.nivcoo.supermission.superiorskyblock.permissions;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.PluginInitializeEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

public class TempFly implements Listener, CommandExecutor {

    private static IslandPrivilege TEMP_FLY;

    private final String cancelMessage = "§7[§c§lES§7] Cette île ne permet pas de fly, votre fly est donc désactivé.";

    @EventHandler
    public void init(PluginInitializeEvent e) {
        IslandPrivilege.register("TEMP_FLY");
        TEMP_FLY = IslandPrivilege.getByName("TEMP_FLY");
    }

    private void setFly(Player p) {
        if (!p.isFlying() || !p.getAllowFlight() || p.getGameMode().equals(GameMode.CREATIVE))
            return;
        Island island = SuperiorSkyblockAPI.getIslandAt(p.getLocation());
        if (island == null)
            return;

        if (!island.hasPermission(p, TEMP_FLY)) {
            p.sendMessage(cancelMessage);
            p.setFlying(false);
            p.performCommand("fly");
        }
    }

    @EventHandler
    public void onMoveEvent(PlayerMoveEvent e) {
        setFly(e.getPlayer());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        Player p = (Player) sender;
        if (command.getName().contains("fly")) {

            Island island = SuperiorSkyblockAPI.getIslandAt(p.getLocation());
            if (island == null)
                return true;

            return island.hasPermission(p, TEMP_FLY);

        }
        return true;
    }

    @EventHandler
    public void preProcessCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("/fly") || e.getMessage().equalsIgnoreCase("/tempfly") || e.getMessage().equalsIgnoreCase("/tempfly:tempfly")) {

            Island island = SuperiorSkyblockAPI.getIslandAt(p.getLocation());
            if (island == null)
                return;
            if (!island.hasPermission(p, TEMP_FLY)) {
                p.sendMessage(cancelMessage);
                e.setCancelled(true);
            }

        }
    }

}
