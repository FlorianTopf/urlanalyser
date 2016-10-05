package com.immobilienscout.urlanalyzer;

import java.util.Map;

public class Analysis {

    private String title;
    private String htmlVersion;
    private Map<String, Integer> headingsCount;
    private Map<String, Integer> linksCount;
    private Boolean hasLogin;
    private String error;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlVersion() {
        return htmlVersion;
    }

    public void setHtmlVersion(String htmlVersion) {
        this.htmlVersion = htmlVersion;
    }

    public Map<String, Integer> getHeadingsCount() {
        return headingsCount;
    }

    public void setHeadingsCount(Map<String, Integer> headingsCount) {
        this.headingsCount = headingsCount;
    }

    public Map<String, Integer> getLinksCount() {
        return linksCount;
    }

    public void setLinksCount(Map<String, Integer> linksCount) {
        this.linksCount = linksCount;
    }

    public Boolean getHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(Boolean hasLogin) {
        this.hasLogin = hasLogin;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
