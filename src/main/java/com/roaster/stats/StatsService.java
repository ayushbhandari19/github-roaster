package com.roaster.stats;

import com.roaster.github.GitHubRepo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatsService {

    public DeveloperStats analyze(List<GitHubRepo> repos) {
        int stars = repos.stream().mapToInt(GitHubRepo::getStargazers_count).sum();

        String best = repos.stream()
                .max(Comparator.comparingInt(GitHubRepo::getStargazers_count))
                .map(GitHubRepo::getName)
                .orElse("none");

        String worst = repos.stream()
                .min(Comparator.comparingInt(GitHubRepo::getStargazers_count))
                .map(GitHubRepo::getName)
                .orElse("none");

        Map<String, Long> langs = new HashMap<>();
        for (GitHubRepo r : repos) {
            if (r.getLanguage() != null)
                langs.put(r.getLanguage(), langs.getOrDefault(r.getLanguage(), 0L) + 1);
        }

        String mainLang = langs.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");

        return new DeveloperStats(stars, best, worst, mainLang);
    }
}
