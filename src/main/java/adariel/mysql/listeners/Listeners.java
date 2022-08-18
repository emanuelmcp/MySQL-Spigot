package adariel.mysql.listeners;

import adariel.mysql.MySQL;
import adariel.mysql.models.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.sql.SQLException;

public class Listeners implements Listener {
    private final MySQL plugin;

    public Listeners(MySQL plugin) {
        this.plugin = plugin;
    }

    //Actualizando estadísticas de muerte (tanto para asesino como para el muerto)
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){

        try{
            Player killer = e.getEntity().getKiller();
            Player p = e.getEntity();
            PlayerStats pStats = getPlayerStatsFromDatabase(p);
            pStats.setDeaths(pStats.getDeaths()+1);
            this.plugin.getDatabase().updatePlayerStats(pStats);

            if (killer == null){
                return;
            }
            PlayerStats killerStats = getPlayerStatsFromDatabase(killer);
            killerStats.setKills(killerStats.getKills()+1);
            killerStats.setBalance(killerStats.getBalance()+1);

            this.plugin.getDatabase().updatePlayerStats(killerStats);

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    //Actualizando estadisticas de bloques rotos
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        try {
            PlayerStats stats = getPlayerStatsFromDatabase(p);
            stats.setBlocksBroken(stats.getBlocksBroken()+1);
            stats.setBalance(stats.getBalance() + 0.5);
            this.plugin.getDatabase().updatePlayerStats(stats);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    //Fecha de conexión
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try {
            PlayerStats playerStats = getPlayerStatsFromDatabase(p);
            playerStats.setLastLogin(new Date());
            this.plugin.getDatabase().updatePlayerStats(playerStats);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Fecha de desconexion
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        try {
            PlayerStats playerStats = getPlayerStatsFromDatabase(p);
            playerStats.setLastLogout(new Date());
            this.plugin.getDatabase().updatePlayerStats(playerStats);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Metodo para obtener estadisticas
    private  PlayerStats getPlayerStatsFromDatabase(Player p) throws SQLException{
        PlayerStats stats = this.plugin.getDatabase().findPlayerStatsByUUID(p.getUniqueId().toString());
        //Si no existe el jugador, se le asignan unas estadisticas por defecto
        if (stats == null){
            stats = new PlayerStats(p.getUniqueId().toString(), 0,0,1, 0, new Date(), new Date());
            this.plugin.getDatabase().createPlayerStats(stats);
            return stats;
        }

        return stats;
    }
}
