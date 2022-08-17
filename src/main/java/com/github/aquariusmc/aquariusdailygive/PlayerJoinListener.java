package com.github.aquariusmc.aquariusdailygive;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

/**
 * This class will handle the PlayerJoinEvent. See the {@link PlayerJoinListener#onPlayerJoin(PlayerJoinEvent)} function
 * for more details
 *
 * @see org.bukkit.event.Listener
 * @see DailyManager
 * @see AquariusDailyGive
 * @author LoRy24
 * @version 1.0.0-RELEASE
 */
public class PlayerJoinListener implements Listener {

    /**
     * This function will manage the PlayerJoinEvent. It will check if the joined player can claim his daily reward, and
     * if so it will send a message after 3 seconds.
     *
     * @param event The JoinEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {

        // Check if the player can claim, and if so send him a message after 3 secs
        if (AquariusDailyGive.INSTANCE.getDailyManager().canClaim(event.getPlayer().getName()))
            Bukkit.getScheduler().scheduleSyncDelayedTask(AquariusDailyGive.INSTANCE.getPlugin(), () -> event.getPlayer().sendMessage(ConfigValues.YOU_CAN_CLAIM_A_REWARD_MESSAGE
                    .getStringWithColors()), 60);
    }
}
