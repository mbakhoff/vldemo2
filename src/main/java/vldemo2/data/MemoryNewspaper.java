package vldemo2.data;

import vldemo2.model.Article;
import vldemo2.model.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryNewspaper implements NewspaperProvider {

    private final Map<Integer, Article> articles;

    public MemoryNewspaper() {
        List<Comment> firstArticleComments = new ArrayList<>();
        firstArticleComments.add(new Comment(1, 1, "i love servlets"));
        firstArticleComments.add(new Comment(2, 1, "xml config is messy"));

        List<Comment> secondArticleComments = new ArrayList<>();
        secondArticleComments.add(new Comment(3, 2, "i'm all alone here"));

        articles = new HashMap<>();
        articles.put(1, new Article(1, "demo #1",
                "Servlet 3.0 provides the @WebServlet annotation to define a servlet",
                firstArticleComments));
        articles.put(2, new Article(2, "demo #2",
                "@WebServlet obviates the need for configuration in web.xml",
                secondArticleComments));
    }

    @Override
    public Article findArticleById(int id) {
        return articles.get(id);
    }

    @Override
    public List<Article> findAllArticles() {
        return new ArrayList<>(articles.values());
    }
}
