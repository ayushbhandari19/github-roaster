package com.roaster.api;

import com.roaster.github.GitHubClient;
import com.roaster.stats.StatsService;
import com.roaster.roast.RoastService;
import com.roaster.db.RoastCount;
import com.roaster.db.RoastCountRepo;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RoastController {

    private final GitHubClient github;
    private final StatsService stats;
    private final RoastService roast;
    private final RoastCountRepo repo;

    public RoastController(GitHubClient github, StatsService stats, RoastService roast, RoastCountRepo repo) {
        this.github = github;
        this.stats = stats;
        this.roast = roast;
        this.repo = repo;
    }

    @GetMapping("/roast/{username}")
    public Mono<?> roast(@PathVariable String username) {
        return github.getRepos(username)
                .map(stats::analyze)
                .flatMap(s -> roast.roast(s)
                        .flatMap(r -> Mono.fromCallable(() -> {
                            var rc = repo.findById(username)
                                    .orElse(new RoastCount(username));
                            rc.inc();
                            repo.save(rc);

                            return Map.of(
                                    "stats", s,
                                    "roast", r,
                                    "roastCount", rc.getCount());
                        }).subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic())));
    }
}
