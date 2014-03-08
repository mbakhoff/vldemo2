package vldemo2.datastore;

import vldemo2.auctiondata.Bid;
import vldemo2.auctiondata.Item;

import java.util.List;

public interface AuctionDataProvider {

    public Item findItemById(int id);
    public List<Item> findAllItems();
    public void addBid(Bid bid);

}
