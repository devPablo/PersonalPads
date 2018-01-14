package org.bonilla.personalpads;

import org.bonilla.personalpads.commands.Commandpersonalpads;
import org.bonilla.personalpads.events.PlayerMove;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PersonalPads extends JavaPlugin {

    public void onEnable() {
        registerEvent();
        registerConfig();
        registerCommand();

        getLogger().info("Successfully enabled.");
    }

    public void onDisable() {
        getLogger().info("Successfully disabled.");
    }

    public void registerEvent() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerMove(this), this);
    }

    public void registerConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public void registerCommand() {
        getCommand("personalpads").setExecutor(new Commandpersonalpads(this));
    }
}
