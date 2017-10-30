package com.soundbread.history.data.network.model;

/**
 * Created by fruitbites on 2017-09-17.
 */

public class Search {
    String id;
    int time;
    String tp;
    String dynasty;
    String name;
    String chinese;
    String desc;
    String event;
    String remains;
    String organization;
    String period;

    public Search(String id, int time, String tp, String dynasty, String name, String chinese, String desc, String event, String remains, String organization, String period) {
        this.id = id;
        this.time = time;
        this.tp = tp;
        this.dynasty = dynasty;
        this.name = name;
        this.chinese = chinese;
        this.desc = desc;
        this.event = event;
        this.remains = remains;
        this.organization = organization;
        this.period = period;
    }
    public Search() {
        this("",0,"","","","","","","","","");
    }

    public Search(String s) {
        String[] tokens = s.split("\\^@\\^",-1);
        for(int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            switch(i) {
                case 0: this.id = token; break;
                case 1: try { this.time = Integer.parseInt(token);} catch(Exception ignore) { } break;
                case 2: this.tp = token; break;
                case 3: this.dynasty = token; break;
                case 4: this.name = token; break;
                case 5: this.chinese = token; break;
                case 6: this.desc = token; break;
                case 7: this.event = token; break;
                case 8: this.remains = token; break;
                case 9: this.organization = token; break;
                case 10: this.period = token; break;
            }
        }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getRemains() {
        return remains;
    }

    public void setRemains(String remains) {
        this.remains = remains;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }


    @Override
    public String toString() {
        return "Search{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", tp='" + tp + '\'' +
                ", dynasty='" + dynasty + '\'' +
                ", name='" + name + '\'' +
                ", chinese='" + chinese + '\'' +
                ", desc='" + desc + '\'' +
                ", event='" + event + '\'' +
                ", remains='" + remains + '\'' +
                ", organization='" + organization + '\'' +
                ", period='" + period + '\'' +
                '}';
    }
}
