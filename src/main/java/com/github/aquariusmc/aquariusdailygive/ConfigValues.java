package com.github.aquariusmc.aquariusdailygive;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * This enum will be used to read options from the config.
 */
public enum ConfigValues {
    YOU_CAN_CLAIM_A_REWARD_MESSAGE("settings.onJoinCanClaimMessage"),
    REWARD_ALREADY_CLAIMED("settings.dailyRewardAlreadyClaimedMessage"),
    DAILY_REWARD_CLAIMED("settings.dailyRewardClaimed"),
    COMMANDS_TO_EXECUTE_ON_CLAIM("settings.commandsToRun"),
    ;

    /**
     * The path in the config
     */
    @Getter
    private final String path;

    /**
     * Inject the path of a value in the config.
     */
    ConfigValues(String path) {
        this.path = path;
    }

    /**
     * Get an object from the config
     */
    public Object get() {
        return AquariusDailyGive.INSTANCE.getPlugin().getConfig().get(this.path); // Get by the path
    }

    /**
     * Get a string with colors from the config
     */
    @NotNull
    @Contract(" -> new")
    public String getStringWithColors() {
        // Use the get function defined in this enum
        return ChatColor.translateAlternateColorCodes('&', (String) this.get());
    }
}
