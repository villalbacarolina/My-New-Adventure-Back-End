package com.mynewadventure.services;

import com.mynewadventure.models.Tag;
import com.mynewadventure.models.UserActivity;
import com.mynewadventure.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag getTag(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public void deleteTag(Long tagId) {
        Optional<Tag> tagOptional = tagRepository.findById(tagId);
        if (tagOptional.isPresent()) {
            Tag tag = tagOptional.get();
            tagRepository.delete(tag);
        } else {
            throw new RuntimeException("Tag not found with id: " + tagId);
        }
    }
}
