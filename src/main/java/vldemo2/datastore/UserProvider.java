package vldemo2.datastore;

import vldemo2.auctiondata.User;

import java.sql.SQLException;

public interface UserProvider {

    User getById(int id) throws SQLException;

}
