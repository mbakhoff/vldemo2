package vldemo2.datastore;

import vldemo2.auctiondata.SimpleBid;

import java.sql.SQLException;
import java.util.List;

public interface BidsRepository {
    List<SimpleBid> getBidsByUser(int userId) throws SQLException;
}
