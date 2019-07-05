package com.example.firstaidassistance.NearbyPlaceModels.ModelClasses;

import java.util.List;

public class Myplaces {

    private List<Results> results;

    private List<String>html_attributions;

    private String status;


    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }


    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+results+", html_attributions = "+html_attributions+", status = "+status+"]";
    }


}
