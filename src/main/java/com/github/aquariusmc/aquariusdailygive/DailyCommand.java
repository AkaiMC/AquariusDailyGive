package com.github.aquariusmc.aquariusdailygive;

import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The /daily command. This will make a player claim his reward, using the DailyManager as tool.
 *
 * @see DailyManager
 * @author LoRy24
 * @version 1.0.0-SNAPSHOT
 */
public class DailyCommand implements CommandExecutor, TabCompleter {

    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // If the sender is the console, return
        if (sender instanceof ConsoleCommandSender) return false;

        // If he cannot claim
        if (!AquariusDailyGive.INSTANCE.getDailyManager().canClaim(sender.getName())) {
            sender.sendMessage(ConfigValues.REWARD_ALREADY_CLAIMED.getStringWithColors()); // Send the message
            return false;
        }

        // Claim the reward & notify
        sender.sendMessage(ConfigValues.REWARD_ALREADY_CLAIMED.getStringWithColors());
        AquariusDailyGive.INSTANCE.getDailyManager().claim(sender.getName());
        return true;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
