package com.github.aquariusmc.aquariusdailygive;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class is the entry of the plugin. This will start the plugin's core and handle the onDisable plugin's event.
 *
 * @see AquariusDailyGive
 * @author LoRy24
 * @version 1.0.0-RELEASE
 */
@SuppressWarnings("unused")
public final class Entry extends JavaPlugin {

    /**
     * This function will be called when the plugin gets enabled
     */
    @Override
    public void onEnable() {
        AquariusDailyGive.INSTANCE.enable(this);
    }

    /**
     * This function will be called when the plugin disables
     */
    @Override
    public void onDisable() {
        AquariusDailyGive.INSTANCE.disable();
    }
}
