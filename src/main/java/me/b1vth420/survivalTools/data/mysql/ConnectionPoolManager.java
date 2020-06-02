package me.b1vth420.survivalTools.data.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {

    private final Main plugin;
    private HikariDataSource dataSource;

    public ConnectionPoolManager(Main plugin) {
        this.plugin = plugin;
        setupPool(Config.getInst().mysqlIP, Config.getInst().mysqlPort, Config.getInst().mysqlDatabase, Config.getInst().mysqlUser, Config.getInst().mysqlPassword);
    }


    private void setupPool(String hostname, int port, String database, String username, String password) {
        this.dataSource = new HikariDataSource();

        this.dataSource.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?characterEncoding=utf8");
        this.dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        this.dataSource.setUsername(username);
        this.dataSource.setPassword(password);
        this.dataSource.setPoolName("HikariMySQL");
        this.dataSource.addDataSourceProperty("cachePrepStmts", true);
        this.dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        this.dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.dataSource.addDataSourceProperty("useServerPrepStmts", true);
        this.dataSource.addDataSourceProperty("useLocalSessionState", true);
        this.dataSource.addDataSourceProperty("rewriteBatchedStatements", true);
        this.dataSource.addDataSourceProperty("cacheResultSetMetadata", true);
        this.dataSource.addDataSourceProperty("cacheServerConfiguration", true);
        this.dataSource.addDataSourceProperty("elideSetAutoCommits", true);
        this.dataSource.addDataSourceProperty("maintainTimeStats", false);
        this.dataSource.addDataSourceProperty("autoClosePStmtStreams", true);
        this.dataSource.setAutoCommit(true);
        this.dataSource.setMaxLifetime(480000);
        this.dataSource.setMaximumPoolSize(10);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
