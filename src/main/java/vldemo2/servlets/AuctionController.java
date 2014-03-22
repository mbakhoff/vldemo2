package vldemo2.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import vldemo2.auctiondata.Bid;
import vldemo2.auctiondata.Item;
import vldemo2.datastore.AuctionDataProvider;
import vldemo2.datastore.MemoryAuctionData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/auctions")
public class AuctionController extends HttpServlet {

    private Gson gson;
    private AuctionDataProvider datastore;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        datastore = new MemoryAuctionData();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");

        String idString = req.getParameter("id");
        if (idString != null) {
            replyWithSingleItem(resp, idString);
        } else {
            replyWithAllItems(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Bid bid = gson.fromJson(req.getReader(), Bid.class);
            datastore.addBid(bid); // bid should be validated carefully

            // echo the same object back for convenience and debugging
            // also it now contains the generated id of the bid
            String bidEcho = gson.toJson(bid);
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().write(bidEcho);

            // actually this is a bad place to send the broadcast.
            // better: attach sockets as eventlisteners to the datastore
            // even better: use message queues for servlet-datastore events
            AuctionSocketController.find(req.getServletContext()).broadcast(bidEcho);
        } catch (JsonParseException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }

    private void replyWithAllItems(HttpServletResponse resp) throws IOException {
        List<Item> allContent = datastore.findAllItems();
        resp.getWriter().write(gson.toJson(allContent));
    }

    private void replyWithSingleItem(HttpServletResponse resp, String idString) throws IOException {
        int id = Integer.parseInt(idString);
        Item item = datastore.findItemById(id);
        resp.getWriter().write(gson.toJson(item));
    }

}
