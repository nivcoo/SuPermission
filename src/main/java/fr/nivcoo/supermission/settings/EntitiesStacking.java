package fr.nivcoo.supermission.settings;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.PluginInitializeEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandFlag;
import com.bgsoftware.wildstacker.api.events.EntityStackEvent;
import com.bgsoftware.wildstacker.api.events.SpawnerStackedEntitySpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntitiesStacking implements Listener {
    private static IslandFlag ENTITIES_STACKING;

    @EventHandler
    public void init(PluginInitializeEvent e) {
        IslandFlag.register("ENTITIES_STACKING");
        ENTITIES_STACKING = IslandFlag.getByName("ENTITIES_STACKING");
    }

    @EventHandler
    public void onEntityStack(EntityStackEvent e) {
        Island island = SuperiorSkyblockAPI.getIslandAt(e.getTarget().getLocation());
        if (island != null && !island.hasSettingsEnabled(ENTITIES_STACKING))
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityStackedSpawn(SpawnerStackedEntitySpawnEvent e) {
        Island island = SuperiorSkyblockAPI.getIslandAt(e.getSpawner().getLocation());
        if (island != null && !island.hasSettingsEnabled(ENTITIES_STACKING))
            e.setShouldBeStacked(false);
    }
}
