package com.github.aquariusmc.aquariusdailygive;

import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The daily manager is the core of this plugin. It contains some important functions used to get infos about given daily
 * gifts. With this class, you can get the time of when a player claimed his gift last, if the user can claim his price, and
 * you can also make a player claim his reward. For more details, check the code above
 *
 * @see DailyCommand
 * @see Database
 * @author LoRy24
 * @version 1.0.0-RELEASE
 */
public class DailyManager {

    /**
     * This function will return the millis of when the player has claimed his gift last.
     *
     * @param username The user that the function is going to check
     * @return The millis value
     */
    public long getLastClaimMillis(String username) {
        if (!isPresentInDatabase(username)) return -1L; // If the user is not present in the database

        try {
            ResultSet resultSet = AquariusDailyGive.INSTANCE.getDatabase().executeQuery("SELECT * FROM daily_data WHERE PlayerName='" + username + "';");
            resultSet.next();
            long millis = resultSet.getLong("LastClaimMillis");
            resultSet.close();
            resultSet.getStatement().close();
            AquariusDailyGive.INSTANCE.getDatabase().getConnection().close();
            return millis;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This function will check if a user is inside the database.
     *
     * @param username The user to check
     * @return If the user is present or not
     */
    public boolean isPresentInDatabase(String username) {
        try {
            ResultSet resultSet = AquariusDailyGive.INSTANCE.getDatabase().executeQuery("SELECT * FROM daily_data");
            while (resultSet.next()) {
                if (!resultSet.getString("PlayerName").equals(username)) continue;
                return true;
            }
            resultSet.close();
            resultSet.getStatement().close();
            AquariusDailyGive.INSTANCE.getDatabase().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * This function will check if a user can claim is gift by checking if it's present in the db and if the current time
     * in millis is > of the last claim millis + 24h.
     *
     * @param username The user to check
     * @return If the user can claim
     */
    public boolean canClaim(String username) {
        return !isPresentInDatabase(username) || System.currentTimeMillis() >= (getLastClaimMillis(username) + (1000 * 60 * 60 * 24));
    }

    /**
     * This function will make a player claim his reward.
     *
     * @param username The user that is going to claim his reward.
     */
    @SuppressWarnings("unchecked")
    public void claim(String username) {
        // Update the millis
        AquariusDailyGive.INSTANCE.getDatabase().executeUpdate(isPresentInDatabase(username) ? "UPDATE daily_data SET LastClaimMillis=" + System.currentTimeMillis() + " WHERE PlayerName='" + username + "';" :
                "INSERT INTO daily_data(PlayerName, LastClaimMillis) VALUES('" + username + "', " + System.currentTimeMillis() +");");

        // Execute the commands
        List<String> commands = (List<String>) ConfigValues.COMMANDS_TO_EXECUTE_ON_CLAIM.get();
        commands.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                s.replace("%player%", username)));
    }
}
