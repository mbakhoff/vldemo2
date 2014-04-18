package vldemo2.servlets;

import com.mchange.v2.c3p0.DataSources;
import vldemo2.auctiondata.SimpleBid;
import vldemo2.auctiondata.User;
import vldemo2.datastore.BidsRepository;
import vldemo2.datastore.DatabaseBidsRepository;
import vldemo2.datastore.DatabaseUserProvider;
import vldemo2.datastore.UserProvider;
import vldemo2.service.HighestBids;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

public class BadCode extends HttpServlet {

    private UserProvider userProvider;
    private BidsRepository repository;
    private HighestBids highestBids;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        // set up here or use dependency injection (for example google-guice)
        try {
            DataSource dataSource = createHardcodedDatasource();
            userProvider = new DatabaseUserProvider(dataSource);
            repository = new DatabaseBidsRepository(dataSource);
            highestBids = new HighestBids(repository);
        } catch (SQLException ex) {
            throw new RuntimeException("failed to setup database", ex);
        }
    }

    /*
     * a datasource is just an object with method getConnection().
     * configure the datasource once and use it everywhere, this way you don't
     * have to deal with the URIs and passwords all the time
     */
    private static DataSource createHardcodedDatasource() throws SQLException {
        // dependency: mchange.c3p0
        // homepage and tutorial: http://www.mchange.com/projects/c3p0/
        return DataSources.unpooledDataSource(
                "jdbc:postgresql://localhost/testdb",
                "vldemo2", "vldemo2");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * note that the servlet doesn't have any business logic or
         * database access code in it. these components are extracted into
         * separately testable and developable classes.
         *
         * actually the servlet shouldn't output html either. that's a job
         * for velocity or JSPs. ideally the servlet will only decide which
         * view (velocity or jsp template) to use and gather the data
         * for the view from different services
         */
        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            User user = userProvider.getById(userId);
            Map<Integer, SimpleBid> highest = highestBids.highestBids(userId);

            resp.setContentType("text/html; charset=UTF-8");
            printBidsTable(resp.getWriter(), user, highest);
        } catch (SQLException e) {
            /*
             * don't catch exceptions you can't handle!
             * add exception to method signature (.. throws CheckedException) or
             * or rethrow it as a RuntimeException
             *
             * in this case it would also be ok to write a error message to
             * the http response:
             * resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
             */
            throw new RuntimeException(e);
        }
    }

    private void printBidsTable(PrintWriter out, User user, Map<Integer, SimpleBid> bids) throws IOException {
        out.print("<html><body>");
        out.print(String.format("<h1>%s (since %s)</h1>", user.name, user.memberSince));
        out.print("<h2>highest bid by item</h2>");
        out.print("<table>");
        out.print("<tr><td>item id</td><td>highest bid</td></tr>");
        for (Map.Entry<Integer, SimpleBid> bid : bids.entrySet()) {
            out.print("<tr><td>");
            out.print(bid.getKey()); // item id
            out.print("</td><td>");
            out.print(bid.getValue().sum); // bid for that item
            out.print("</td></tr>");
        }
        out.print("</table>");
        out.print("</body></html>");
    }

}

