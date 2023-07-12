package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommandFactionLeave extends SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "leave the faction youre in";
    }

    @Override
    public String getSyntax() {
        return "/f leave";
    }

    @Override
    public boolean perform(Player player, String[] args) {
        try {
            String sql = "DELETE FROM faction_members WHERE player_uuid=?";
            HcfSqlConnection conn = new HcfSqlConnection();
            PreparedStatement statement = conn.getConnection().prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();
            conn.closeConnection();

            player.sendMessage("Successfully Left faction!");
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return true;
    }
}
