package com.soundbread.history.data.db.model;

/**
 * Created by fruitbites on 2017-09-17.
 */

public class Content {
    String id;
    int ord;
    String title;
    String content;

    public Content() {
        this("",-1,"","");
    }

    public Content(String id, int ord, String title, String content) {
        this.id = id;
        this.ord = ord;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
