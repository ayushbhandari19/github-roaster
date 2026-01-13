package com.roaster.stats;

public record DeveloperStats(
        int stars,
        String bestRepo,
        String worstRepo,
        String primaryLanguage
) {}
