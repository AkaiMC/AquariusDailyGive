package com.github.aquariusmc.aquariusdailygive;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * This class will handle the plugin's database features. When this class gets instanced, it will set up the entire
 * database, 3first creating the file (if not exists obviously) and then creating the tables. To execute updates or
 * queries, you can use the functions {@link Database#executeUpdate(String)} & {@link Database#executeQuery(String)}.
 * Remember to always close the connection after doing queries!
 *
 * @author LoRy24
 * @version 1.0.0-RELEASE
 */
@SuppressWarnings("SpellCheckingInspection")
public class Database {

    /**
     * The database's file
     */
    @Getter
    private final File databaseFile;

    /**
     * The connection obj
     */
    private Connection connection;

    /**
     * The constructor that will create the files and set up the sqlite database by creating the main table.
     *
     * @throws IOException If an error occurs while creating the files
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Database() throws IOException {
        this.databaseFile = new File(AquariusDailyGive.INSTANCE.getPlugin().getDataFolder(), "database.sqlite");
        if (!AquariusDailyGive.INSTANCE.getPlugin().getDataFolder().exists()) AquariusDailyGive.INSTANCE.getPlugin().getDataFolder().mkdir();
        this.databaseFile.createNewFile();
        this.setupDatabase();
    }

    /**
     * Set up the database
     */
    private void setupDatabase() {
        this.executeUpdate("CREATE TABLE IF NOT EXISTS daily_data(PlayerName TEXT, LastClaimMillis BIGINT);");
    }

    /**
     * Execute a query in the sqlite database. Remember to close the connection after using this!
     *
     * @param sql The query's SQL string
     * @return The result of the query. This contains all the obtained data.
     */
    public ResultSet executeQuery(String sql) {
        try {
            Statement statement = this.getConnection().createStatement();
            return statement.executeQuery(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This function will execute an SQL update into the database. It will automatically close the connection & the
     * statement
     *
     * @param sql The update SQL string
     */
    public void executeUpdate(String sql) {
        try {
            Statement statement = this.getConnection().createStatement();
            statement.executeUpdate(sql);
            statement.close();
            this.connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function will create a connection to the database. It will use the jdbc driver included by the xerial
     * jdbc-sqlite lib. For optimization purposes, the connection will be saved in this class, in order to reuse it
     * instead of creating a new one. A good doing is to close the connection after using it. Obiouvsly, if the stored
     * connection is closed, it will create a new one.
     *
     * @return The connection
     */
    public Connection getConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) return this.connection;
            Class.forName("org.sqlite.JDBC"); // Include the driver
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseFile.getAbsolutePath());
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.connection;
    }
}
