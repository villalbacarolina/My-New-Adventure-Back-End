package com.mynewadventure.controllers;

import com.mynewadventure.models.UserActivity;
import com.mynewadventure.services.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userActivities")
public class UserActivityController {

    @Autowired
    private UserActivityService userActivityService;

    @PostMapping
    public ResponseEntity<UserActivity> createUserActivity(@RequestBody UserActivity userActivity) {
        return ResponseEntity.ok(userActivityService.createUserActivity(userActivity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserActivity> getUserActivity(@PathVariable Long id) {
        return ResponseEntity.ok(userActivityService.getUserActivity(id));
    }

    @GetMapping
    public ResponseEntity<List<UserActivity>> getUserActivities() {
        return ResponseEntity.ok(userActivityService.getUserActivities());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserActivity> updateUserActivity(@PathVariable Long id, @RequestBody UserActivity userActivityDetails) {
        return ResponseEntity.ok(userActivityService.updateUserActivity(id, userActivityDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserActivity(@PathVariable Long id) {
        userActivityService.deleteUserActivity(id);
        return ResponseEntity.noContent().build();
    }
}
