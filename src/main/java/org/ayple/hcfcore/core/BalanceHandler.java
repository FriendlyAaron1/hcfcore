package org.ayple.hcfcore.core;

import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BalanceHandler {


    // these functions don't check the original amount, that has to be done before hand
    // TODO: make it check the original amount (maybe)
    public static void giveMoneyToPlayer(Player player, int amount) throws SQLException {
        String sql = "UPDATE player_data SET balance= balance + ? WHERE player_id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setInt(1, amount);
        statement.setString(2, player.getUniqueId().toString());
        statement.executeUpdate();

        System.out.println(statement);

        conn.closeConnection();
    }

    public static void takeMoneyFromPlayer(Player player, int amount) throws SQLException {
        String sql = "UPDATE player_data SET balance= balance - ? WHERE player_id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setInt(1, amount);
        statement.setString(2, player.getUniqueId().toString());
        statement.executeUpdate();

        System.out.println(statement);

        conn.closeConnection();
    }

    public static void takeMoneyFromFaction(UUID faction_id, int amount) throws SQLException {
        String sql = "UPDATE factions SET faction_bal = faction_bal - ? WHERE id= ?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setInt(1, amount);
        statement.setString(2, faction_id.toString());
        statement.executeUpdate();

        conn.closeConnection();
    }

    public static void giveMoneyToFaction(UUID faction_id, int amount) throws SQLException {
        String sql = "UPDATE factions SET faction_bal = faction_bal + ? WHERE id= ?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setInt(1, amount);
        statement.setString(2, faction_id.toString());
        statement.executeUpdate();

        conn.closeConnection();
    }

    public static int getPlayerBalance(Player player) throws SQLException {
        String sql = "SELECT balance FROM player_data WHERE player_id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, player.getUniqueId().toString());
        statement.executeQuery();

        ResultSet results = statement.getResultSet();
        if (!results.next()) {
            return 0;
        }

        int bal = results.getInt("balance");

        conn.closeConnection();
        return bal;
    }

}
