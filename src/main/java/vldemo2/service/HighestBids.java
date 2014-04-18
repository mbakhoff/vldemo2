package vldemo2.service;

import vldemo2.auctiondata.SimpleBid;
import vldemo2.datastore.BidsRepository;

import java.sql.SQLException;
import java.util.*;

public class HighestBids {

    private final BidsRepository repository;

    public HighestBids(BidsRepository repository) {
        this.repository = repository;
    }

    public Map<Integer, SimpleBid> highestBids(int userId) throws SQLException {
        List<SimpleBid> allBids = repository.getBidsByUser(userId);
        Map<Integer, SimpleBid> highest = new HashMap<>();
        for (int itemId : getUniqueIds(allBids)) {
            highest.put(itemId, getHighestBid(allBids, itemId));
        }
        return highest;
    }

    private SimpleBid getHighestBid(List<SimpleBid> allBids, int itemId) {
        List<SimpleBid> bidsForItem = getBidsByItem(allBids, itemId);
        sortDescending(bidsForItem);
        return bidsForItem.get(0);
    }

    private void sortDescending(List<SimpleBid> bidsForItem) {
        Collections.sort(bidsForItem, new Comparator<SimpleBid>() {
            @Override
            public int compare(SimpleBid o1, SimpleBid o2) {
                return -Float.compare(o1.sum, o2.sum);
            }
        });
    }

    private List<SimpleBid> getBidsByItem(List<SimpleBid> allBids, int itemId) {
        List<SimpleBid> bidsForItem = new ArrayList<>();
        for (SimpleBid bid : allBids) {
            if (bid.item == itemId) {
                bidsForItem.add(bid);
            }
        }
        return bidsForItem;
    }

    private Set<Integer> getUniqueIds(List<SimpleBid> allBids) {
        Set<Integer> itemIds = new HashSet<>();
        for (SimpleBid bid : allBids) {
            itemIds.add(bid.item);
        }
        return itemIds;
    }

}
