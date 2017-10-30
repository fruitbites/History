package com.soundbread.history.data.db.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fruitbites on 2017-09-17.
 */

public class Incident {
    String id;
    int time;
    String tp;
    String dynasty;
    String name;
    String chinese;
    String photo;
    String thumbOn;
    String thumbOff;
    String desc;
    String birth;
    String alias;
    String period;
    String event;
    String materials;
    String remains;
    String person;
    String organization;
    String antecedents;

    List<Content> contents;

    public Incident() {
        this("",0,"","","","","","","","","","","","","","","","","",new ArrayList<Content>());
    }

    public Incident(String id, int time, String tp, String dynasty, String name, String chinese, String photo, String thumbOn, String thumbOff, String desc, String birth, String alias, String period, String event, String materials, String remains, String person, String oranization, String antecedents, List<Content> contents) {
        this.id = id;
        this.time = time;
        this.tp = tp;
        this.dynasty = dynasty;
        this.name = name;
        this.chinese = chinese;
        this.photo = photo;
        this.thumbOn = thumbOn;
        this.thumbOff = thumbOff;
        this.desc = desc;
        this.birth = birth;
        this.alias = alias;
        this.period = period;
        this.event = event;
        this.materials = materials;
        this.remains = remains;
        this.person = person;
        this.organization = oranization;
        this.antecedents = antecedents;
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getThumbOn() {
        return thumbOn;
    }

    public void setThumbOn(String thumbOn) {
        this.thumbOn = thumbOn;
    }

    public String getThumbOff() {
        return thumbOff;
    }

    public void setThumbOff(String thumbOff) {
        this.thumbOff = thumbOff;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getRemains() {
        return remains;
    }

    public void setRemains(String remains) {
        this.remains = remains;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAntecedents() {
        return antecedents;
    }

    public void setAntecedents(String antecedents) {
        this.antecedents = antecedents;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
