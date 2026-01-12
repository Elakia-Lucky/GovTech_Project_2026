package com.govtech.controller;

import com.govtech.BestBestRestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/sessions/{sessionId}")
public class BestRestaurantController {

    private final BestRestaurantService BestRestaurantService;


    public BestRestaurantController(BestRestaurantService BestRestaurantService) {
        this.BestRestaurantService = BestRestaurantService;
    }


    @PostMapping("/bestrestaurants")
    public void submitRestaurant(
            @PathVariable UUID sessionId,
            @RequestParam String restaurant,
            @RequestParam String submittedBy) {

        BestRestaurantService.submitRestaurant(sessionId, restaurant, submittedBy);
    }


    @GetMapping("/random")
    public String pickRestaurant(@PathVariable UUID sessionId) {
        return BestRestaurantService.pickRandom(sessionId);
    }
}
