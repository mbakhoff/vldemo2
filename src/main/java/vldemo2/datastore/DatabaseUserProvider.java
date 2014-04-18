package vldemo2.datastore;

import vldemo2.auctiondata.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUserProvider implements UserProvider {

    private final DataSource dataSource;

    public DatabaseUserProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getById(int id) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            ResultSet userQuery = queryForUser(conn, id);
            if (!userQuery.next()) {
                throw new RuntimeException("user not found");
            }
            return new User(
                    id,
                    userQuery.getString("name"),
                    userQuery.getDate("registration_date"));
        } finally {
            if (conn != null) conn.close();
        }
    }

    private ResultSet queryForUser(Connection conn, int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id=?;");
        ps.setInt(1, id);
        return ps.executeQuery();
    }

}
