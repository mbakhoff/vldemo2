package vldemo2.datastore;

import vldemo2.auctiondata.SimpleBid;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseBidsRepository implements BidsRepository {

    private final DataSource dataSource;

    public DatabaseBidsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<SimpleBid> getBidsByUser(int userId) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            ResultSet bidsQuery = queryByUser(conn, userId);

            List<SimpleBid> allBids = new ArrayList<>();
            while (bidsQuery.next()) {
                allBids.add(new SimpleBid(
                        bidsQuery.getInt("item"),
                        bidsQuery.getFloat("sum")));
            }
            return allBids;
        } finally {
            // always close connections
            // finally block is awesome because it will always run
            // no catch block needed!
            if (conn != null) conn.close();
        }
    }

    private ResultSet queryByUser(Connection conn, int userId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM bids WHERE user=?;");
        ps.setInt(1, userId);
        return ps.executeQuery();
    }

}
