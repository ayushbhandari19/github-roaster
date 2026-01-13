package com.roaster.api;

import com.roaster.db.RoastCountRepo;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class LeaderboardController {

    private final RoastCountRepo repo;

    public LeaderboardController(RoastCountRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/leaderboard")
    public List<?> leaderboard() {
        return repo.findAll()
                .stream()
                .sorted((a,b) -> b.getCount() - a.getCount())
                .limit(10)
                .toList();
    }
}
