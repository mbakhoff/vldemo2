package vldemo2.data;

import vldemo2.model.Article;

import java.util.List;

public interface NewspaperProvider {

    public Article findArticleById(int id);
    public List<Article> findAllArticles();

}
