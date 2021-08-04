package com.covid19.simple.app.api;

public class ModelClass {

    String cases,todayCases,deaths,todayDeaths,recovered,todayRecovered,active,country;

    public ModelClass(String cases, String todayCases, String deaths, String todayDeaths, String recovered, String todayRecovered, String active, String country) {
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.todayRecovered = todayRecovered;
        this.active = active;
        this.country = country;
    }

    public String getCases() {
        return cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public String getActive() {
        return active;
    }

    public String getCountry() {
        return country;
    }
}
