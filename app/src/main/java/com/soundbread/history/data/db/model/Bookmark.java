package com.soundbread.history.data.db.model;

import java.util.Date;

/**
 * Created by fruitbites on 2017-09-17.
 */

public class Bookmark {
    String id;
    Date cdtm;
    Date mdtm;

    public Bookmark(String id, Date cdtm, Date mdtm) {
        this.id = id;
        this.cdtm = cdtm;
        this.mdtm = mdtm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCdtm() {
        return cdtm;
    }

    public void setCdtm(Date cdtm) {
        this.cdtm = cdtm;
    }

    public Date getMdtm() {
        return mdtm;
    }

    public void setMdtm(Date mdtm) {
        this.mdtm = mdtm;
    }
}
