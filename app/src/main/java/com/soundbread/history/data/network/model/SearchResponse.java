package com.soundbread.history.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("errormessage")
    @Expose
    private String errormessage;
    @SerializedName("searchtime")
    @Expose
    private Long searchtime;
    @SerializedName("totalcount")
    @Expose
    private Long totalcount;
    @SerializedName("servicecount")
    @Expose
    private Long servicecount;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("values")
    @Expose
    private List<Value> values = null;
    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("start")
    @Expose
    private Long start;
    @SerializedName("errorcode")
    @Expose
    private Long errorcode;
    @SerializedName("orignalquery")
    @Expose
    private String orignalquery;

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public Long getSearchtime() {
        return searchtime;
    }

    public void setSearchtime(Long searchtime) {
        this.searchtime = searchtime;
    }

    public Long getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Long totalcount) {
        this.totalcount = totalcount;
    }

    public Long getServicecount() {
        return servicecount;
    }

    public void setServicecount(Long servicecount) {
        this.servicecount = servicecount;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(Long errorcode) {
        this.errorcode = errorcode;
    }

    public String getOrignalquery() {
        return orignalquery;
    }

    public void setOrignalquery(String orignalquery) {
        this.orignalquery = orignalquery;
    }

}
