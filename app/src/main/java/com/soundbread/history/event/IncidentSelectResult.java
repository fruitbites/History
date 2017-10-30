package com.soundbread.history.event;

/**
 * Created by fruitbites on 2017-09-23.
 */

public class IncidentSelectResult {
    String id;
    public IncidentSelectResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
