package com.github.aquariusmc.aquariusdailygive;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * The main class of this plugin. This will store all the values of the plugin. For example, it will store the database
 * class & the daily manager. It will also implement the commands and the events listeners.
 *
 * @see Database
 * @see DailyCommand
 * @see DailyManager
 * @see PlayerJoinListener
 * @author LoRy24
 * @version 1.0.0-RELEASE
 */
public enum AquariusDailyGive {

    /**
     * Instance enum. Used to access to the instanced values inside this class
     */
    INSTANCE,
    ;

    /**
     * A plugin's reference
     */
    @Getter
    private JavaPlugin plugin;

    /**
     * The plugin's database class
     */
    @Getter
    private Database database;

    /**
     * The core of the plugin
     */
    @Getter
    private DailyManager dailyManager;


    /**
     * Enable the plugin
     * @param plugin The plugin entry reference. Used for injection
     */
    public void enable(JavaPlugin plugin) {
        try {
            this.plugin = plugin;
            this.plugin.saveDefaultConfig();

            // Instance & set up the database
            this.database = new Database();

            // Instance the manager
            this.dailyManager = new DailyManager();

            // Implement the command
            this.getPlugin().getCommand("daily").setExecutor(new DailyCommand());
            this.getPlugin().getCommand("daily").setTabCompleter(new DailyCommand());

            // Register the event
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(),
                    this.getPlugin());

            // Notify the load
            this.getPlugin().getLogger().info("Plugin enabled at version v" + this.getPlugin().getDescription().getVersion() + "!");
        }
        catch (IOException e) { // Catch and disable plugin
            e.printStackTrace();
            this.disable();
        }
    }

    /**
     * Disable the plugin
     */
    public void disable() {
        if (Bukkit.getPluginManager().isPluginEnabled(this.getPlugin())) Bukkit.getPluginManager()
                .disablePlugin(this.getPlugin());
        this.getPlugin().getLogger().info("Plugin disabled! Bye!");
    }
}
