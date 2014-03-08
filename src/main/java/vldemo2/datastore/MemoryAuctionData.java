package vldemo2.datastore;

import vldemo2.auctiondata.Item;
import vldemo2.auctiondata.Bid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryAuctionData implements AuctionDataProvider {

    private final Map<Integer, Item> items;
    private int bidCounter;

    public MemoryAuctionData() {
        List<Bid> firstArticleBids = new ArrayList<>();
        firstArticleBids.add(new Bid(1, 1, "bob", 12f));
        firstArticleBids.add(new Bid(2, 1, "alice", 16f));

        List<Bid> secondArticleBids = new ArrayList<>();
        secondArticleBids.add(new Bid(3, 2, "mike", 22f));

        items = new HashMap<>();
        items.put(1, new Item(1, "Nortal t-shirt",
                "Ultimate geek swag",
                firstArticleBids));
        items.put(2, new Item(2, "Dell Vostro 1310 (used)",
                "Totally awesome laptop. Two cores!",
                secondArticleBids));

        bidCounter = 3;
    }

    @Override
    public Item findItemById(int id) {
        return items.get(id);
    }

    @Override
    public List<Item> findAllItems() {
        return new ArrayList<>(items.values());
    }

    @Override
    public void addBid(Bid bid) {
        items.get(bid.itemId).bids.add(bid);
        bid.id = ++bidCounter; // concurrency bug
    }
}
