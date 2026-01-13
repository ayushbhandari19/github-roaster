package com.roaster.roast;

import com.roaster.stats.DeveloperStats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RoastService {

    @Value("${roast.mock:false}")
    private boolean mock;

    public Mono<String> roast(DeveloperStats stats) {

        if (mock) {
            return Mono.just(mockRoast(stats));
        }

        return Mono.just("Real OpenAI disabled");
    }

    private String mockRoast(DeveloperStats s) {
        if (s.stars() == 0) {
            return "You commit like you're hiding from GitHub but still addicted to pushing empty repos.";
        }
        if (s.primaryLanguage().equalsIgnoreCase("Java")) {
            return "You write Java like it's 2008, but somehow it still works… barely.";
        }
        return "Your GitHub looks productive, but we both know StackOverflow did most of the work.";
    }
}
