package com.roaster.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepo {
    private String name;
    private int stargazers_count;
    private String language;

    public String getName() { return name; }
    public int getStargazers_count() { return stargazers_count; }
    public String getLanguage() { return language; }

    public void setName(String name) { this.name = name; }
    public void setStargazers_count(int s) { this.stargazers_count = s; }
    public void setLanguage(String language) { this.language = language; }
}
