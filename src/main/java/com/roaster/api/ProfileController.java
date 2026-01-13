package com.roaster.api;

import com.roaster.github.GitHubClient;
import com.roaster.stats.StatsService;
import com.roaster.roast.RoastService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
public class ProfileController {

    private final GitHubClient github;
    private final StatsService stats;
    private final RoastService roast;

    public ProfileController(GitHubClient github, StatsService stats, RoastService roast) {
        this.github = github;
        this.stats = stats;
        this.roast = roast;
    }

    @GetMapping("/u/{username}")
    public Mono<String> profile(@PathVariable String username, Model model) {
        return github.getRepos(username)
                .map(stats::analyze)
                .flatMap(s -> roast.roast(s).map(r -> {
                    model.addAttribute("user", username);
                    model.addAttribute("stats", s);
                    model.addAttribute("roast", r);
                    return "profile";
                }));
    }
}
