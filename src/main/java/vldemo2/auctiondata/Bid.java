package vldemo2.auctiondata;

public class Bid {

    public Integer id;
    public Integer itemId;
    public String bidder;
    public Float amount;

    public Bid() {
    }

    public Bid(Integer id, Integer itemId, String bidder, Float amount) {
        this.id = id;
        this.itemId = itemId;
        this.bidder = bidder;
        this.amount = amount;
    }

}
