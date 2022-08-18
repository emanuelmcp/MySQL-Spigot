package adariel.mysql;

import adariel.mysql.db.Database;
import adariel.mysql.listeners.Listeners;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;


public final class MySQL extends JavaPlugin {

    private Database database;


    public Database getDatabase() {
        return database;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        this.database = new Database(
                getConfig().getString("database.host"),
                getConfig().getString("database.port"),
                getConfig().getString("database.user"),
                getConfig().getString("database.password"),
                getConfig().getString("database.database_name")
                );
        try {
            database.initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos");
        }
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
