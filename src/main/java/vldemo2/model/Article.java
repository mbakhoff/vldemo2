package vldemo2.model;

import java.util.List;

public class Article {

    public Integer id;
    public String title;
    public String content;
    public List<Comment> comments;

    public Article() {
    }

    public Article(Integer id, String title, String content, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }

}
