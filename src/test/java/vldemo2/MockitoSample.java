package vldemo2;

import org.junit.Test;
import vldemo2.auctiondata.Bid;
import vldemo2.auctiondata.Item;
import vldemo2.datastore.AuctionDataProvider;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MockitoSample {

    @Test
    public void testProviderMock() throws Exception {
        AuctionDataProvider provider = mock(AuctionDataProvider.class);
        when(provider.findItemById(1)).thenReturn(
                new Item(1, "mockTitle", "mockDescription", new ArrayList<Bid>()));

        Item item = provider.findItemById(1);
        assertEquals((Integer)1, item.id);
        assertEquals("mockTitle", item.title);
    }
}
