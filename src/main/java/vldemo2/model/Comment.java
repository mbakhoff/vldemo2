package vldemo2.model;

public class Comment {

    public Integer id;
    public Integer articleId;
    public String content;

    public Comment() {
    }

    public Comment(Integer id, Integer articleId, String content) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
    }

}
