package com.sns.feedserver.feed;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
public class SocialFeedController {

    private final SocialFeedService feedService;

    @GetMapping
    public List<SocialFeed> getAllFeeds() {
        return feedService.getAllFeeds();
    }

    @GetMapping("/user/{userId}")
    public List<SocialFeed> getAllFeedsByUser(@PathVariable("userId") int userId) {
        return feedService.getAllFeedsByUploaderId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialFeed> getFeedById(@PathVariable int id) {
        try {
            SocialFeed feed = feedService.getFeedById(id);
            return ResponseEntity.ok(feed);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeed(@PathVariable int id) {
        feedService.deleteFeed(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<SocialFeed> createFeed(@RequestBody FeedRequest feed) {
        SocialFeed createdFeed = feedService.createFeed(feed);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeed);
    }
}