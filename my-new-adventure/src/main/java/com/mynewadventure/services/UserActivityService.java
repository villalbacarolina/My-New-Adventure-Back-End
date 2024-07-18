package com.mynewadventure.services;

import com.mynewadventure.models.UserActivity;
import com.mynewadventure.repositories.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserActivityService {

    @Autowired
    private UserActivityRepository userActivityRepository;

    public UserActivity createUserActivity(UserActivity userActivity) {
        return userActivityRepository.save(userActivity);
    }

    public UserActivity updateUserActivity(Long userActivityId, UserActivity userActivityDetails) {
        Optional<UserActivity> userActivityOptional = userActivityRepository.findById(userActivityId);
        if (userActivityOptional.isPresent()) {
            UserActivity userActivity = userActivityOptional.get();
            userActivity.setSavedEvents(userActivityDetails.getSavedEvents());
            userActivity.setPublishedEvents(userActivityDetails.getPublishedEvents());
            return userActivityRepository.save(userActivity);
        } else {
            throw new RuntimeException("UserActivity not found with id: " + userActivityId);
        }
    }

    public UserActivity getUserActivity(Long userActivityId) {
        return userActivityRepository.findById(userActivityId).orElseThrow(() -> new RuntimeException("UserActivity not found with id: " + userActivityId));
    }

    public void deleteUserActivity(Long userActivityId) {
        Optional<UserActivity> userActivityOptional = userActivityRepository.findById(userActivityId);
        if (userActivityOptional.isPresent()) {
            UserActivity userActivity = userActivityOptional.get();
            userActivityRepository.delete(userActivity);
        } else {
            throw new RuntimeException("UserActivity not found with id: " + userActivityId);
        }
    }
}
