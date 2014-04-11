package vldemo2.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class BadCode extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            PrintWriter out = resp.getWriter();
            out.print("<html><body>");

            // get user by id
            int userId = Integer.parseInt(req.getParameter("id"));
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM users WHERE id=?;");
            ps.setInt(1, userId);
            ResultSet userQuery = ps.executeQuery();
            if (!userQuery.next()) {
                throw new IllegalArgumentException("user not found");
            }
            User user = new User(
                    userQuery.getString("name"),
                    userQuery.getDate("registration_date"));

            out.print(String.format("<h1>%s (since %s)</h1>", user.name, user.memberSince));
            out.print("<h2>highest bid by item</h2>");
            out.print("<table>");
            out.print("<tr><td>item id</td><td>highest bid</td></tr>");

            // get bids by user
            PreparedStatement ps2 = getConnection().prepareStatement("SELECT * FROM bids WHERE user=?;");
            ps2.setInt(1, userId);
            ResultSet bidsQuery = ps.executeQuery();
            List<SimpleBid> allBids = new ArrayList<>();
            while (bidsQuery.next()) {
                SimpleBid bid = new SimpleBid(
                        bidsQuery.getInt("item"),
                        bidsQuery.getFloat("sum"));
                allBids.add(bid);
            }

            // get unique bids
            Set<Integer> itemIds = new HashSet<>();
            for (SimpleBid bid : allBids) {
                itemIds.add(bid.item);
            }

            // for each distinct item
            for (int itemId : itemIds) {
                // get bids by item id
                List<SimpleBid> bidsForItem = new ArrayList<>();
                for (SimpleBid bid : allBids) {
                    if (bid.item == itemId) {
                        bidsForItem.add(bid);
                    }
                }

                Collections.sort(bidsForItem);

                // output highest bid
                SimpleBid highestBid = bidsForItem.get(bidsForItem.size()-1);
                out.print(String.format("<tr><td>%d</td><td>%f</td></tr>",
                        itemId, highestBid.sum));
            }

            out.print("</table>");
            out.print("</body></html>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://pg.heroku.com/vldemo2",
                "mbakhoff", "1234");
    }

}

class SimpleBid implements Comparable<SimpleBid> {
    public final int item;
    public final float sum;

    public SimpleBid(int item, float sum) {
        this.item = item;
        this.sum = sum;
    }

    @Override
    public int compareTo(SimpleBid o) {
        return Float.compare(sum, o.sum);
    }
}

class User {
    public final String name;
    public final Date memberSince;

    public User(String name, Date memberSince) {
        this.name = name;
        this.memberSince = memberSince;
    }
}
