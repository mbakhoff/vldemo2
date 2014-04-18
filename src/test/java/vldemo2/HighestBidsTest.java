package vldemo2;

import org.junit.Test;
import vldemo2.auctiondata.SimpleBid;
import vldemo2.datastore.BidsRepository;
import vldemo2.service.HighestBids;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HighestBidsTest {

    public static final float MAX_ROUNDING_ERROR = 0.1f;

    /*
     * junit annotation makes it possible to automatically run the tests.
     * maven will run all junit tests before packaging the application which
     * also means that heroku will run the tests before deploying.
     *
     * you can also run them manually from your IDE. most IDEs support junit.
     */
    @Test
    public void testBids() throws Exception {
        int user = 1;
        int item = 42;
        float bid1 = 49.9f;
        float bid2 = 99.9f;
        float bid3 = 79.9f;

        // make sure you know all the inputs
        HighestBids highestBids = new HighestBids(mockRepository(user,
                new SimpleBid(item, bid1),
                new SimpleBid(item, bid2),
                new SimpleBid(item, bid3)));

        // call the code you want to test
        Map<Integer, SimpleBid> result = highestBids.highestBids(user);

        // check that results are correct
        // args: (message, expected, actual)
        assertEquals("only one item in results",
                1, result.size());
        assertEquals("result is highest bid",
                bid2, result.get(item).sum, MAX_ROUNDING_ERROR);
    }

    private BidsRepository mockRepository(int userId, SimpleBid... bids) throws Exception {
        // mockito magic. interfaces make it possible
        BidsRepository repo = mock(BidsRepository.class);
        when(repo.getBidsByUser(userId)).thenReturn(Arrays.asList(bids));
        return repo;
    }
}
