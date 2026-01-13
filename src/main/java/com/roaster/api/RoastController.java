package com.roaster.api;

import com.roaster.github.GitHubClient;
import com.roaster.stats.StatsService;
import com.roaster.roast.RoastService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RoastController {

    private final GitHubClient github;
    private final StatsService stats;
    private final RoastService roast;

    public RoastController(GitHubClient github, StatsService stats, RoastService roast) {
        this.github = github;
        this.stats = stats;
        this.roast = roast;
    }

    @GetMapping("/roast/{username}")
    public Mono<?> roast(@PathVariable String username) {
        return github.getRepos(username)
                .map(stats::analyze)
                .flatMap(s -> roast.roast(s)
                        .map(r -> Map.of(
                                "stats", s,
                                "roast", r)));
    }

}
