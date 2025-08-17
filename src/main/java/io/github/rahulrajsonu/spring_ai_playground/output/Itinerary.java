package io.github.rahulrajsonu.spring_ai_playground.output;

import java.util.List;

public record Itinerary(List<Activity> activities) {

    public record Activity(String activity, String location, String day, String time) {
        // Additional methods or validations can be added here if needed
    }
}
