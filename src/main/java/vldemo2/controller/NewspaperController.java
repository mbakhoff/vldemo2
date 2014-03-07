package vldemo2.controller;

import com.google.gson.Gson;
import vldemo2.data.MemoryNewspaper;
import vldemo2.data.NewspaperProvider;
import vldemo2.model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/newspaper")
public class NewspaperController extends HttpServlet {

    private Gson gson;
    private NewspaperProvider provider;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        provider = new MemoryNewspaper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");

        String idString = req.getParameter("id");
        if (idString != null) {
            replyWithSingleArticle(resp, idString);
        } else {
            replyWithAllArticles(resp);
        }
    }

    private void replyWithAllArticles(HttpServletResponse resp) throws IOException {
        List<Article> allContent = provider.findAllArticles();
        resp.getWriter().write(gson.toJson(allContent));
    }

    private void replyWithSingleArticle(HttpServletResponse resp, String idString) throws IOException {
        int id = Integer.parseInt(idString);
        Article article = provider.findArticleById(id);
        resp.getWriter().write(gson.toJson(article));
    }
}
