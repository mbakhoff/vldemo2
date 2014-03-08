package vldemo2.auctiondata;

import java.util.List;

public class Item {

    public Integer id;
    public String title;
    public String description;
    public List<Bid> bids;

    public Item() {
    }

    public Item(Integer id, String title, String description, List<Bid> bids) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bids = bids;
    }

}
