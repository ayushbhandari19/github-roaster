package com.roaster.github;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
public class GitHubClient {

    private final WebClient client;

    public GitHubClient(WebClient.Builder builder) {
        this.client = builder
                .baseUrl("https://api.github.com")
                .build();
    }
    @Cacheable("githubRepos")
    public Mono<List<GitHubRepo>> getRepos(String username) {
        return client.get()
                .uri("/users/{u}/repos?per_page=100", username)
                .retrieve()
                .bodyToFlux(GitHubRepo.class)
                .collectList();
    }
}
