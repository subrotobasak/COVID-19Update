package com.example.covid19news.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalModel {
    @SerializedName("cases")
    @Expose
    private Integer cases;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;

    //Constructor
    public GlobalModel(Integer cases, Integer deaths, Integer recovered) {
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    //Getter and Setter Method

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }
}
