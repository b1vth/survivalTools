package me.b1vth420.survivalTools.data.mysql;

import me.b1vth420.survivalTools.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLManager {
    private final Main plugin;
    private final ConnectionPoolManager pool;

    public SQLManager(Main plugin) {
        this.plugin = plugin;
        pool = new ConnectionPoolManager(plugin);
    }

    public void createTable(String statement) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void execute(String statement) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void executeUpdate(String statement, String[] data) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            for (int i = 1; i < data.length + 1; i++) {
                ps.setString(i, data[i - 1]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public Map<String, Object> executeQuery(String statement, String[] data) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        Map<String, Object> row = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            for (int i = 1; i < data.length + 1; i++) {
                ps.setString(i, data[i - 1]);
            }
            result = ps.executeQuery();
            if (result.next()) {
                row = new HashMap<String, Object>();
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    row.put(result.getMetaData().getColumnName(i), result.getObject(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, result);
        }
        return row;
    }

    public List<Map<String, Object>> executeQuery(String statement) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> row = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            result = ps.executeQuery();
            while (result.next()) {
                row = new HashMap<>();
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++)
                    row.put(result.getMetaData().getColumnName(i), result.getObject(i));
                resultList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, result);
        }
        return resultList;
    }

    public void onDisable() {
        pool.closePool();
    }

}
