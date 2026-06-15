package com.copper.Nasir.Service;

import com.copper.Nasir.Entity.Media;
import com.copper.Nasir.Repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Optional;
import java.util.UUID;
 
@Service
public class MediaService {
 
    private final MediaRepository repository;
 
    @Autowired
    public MediaService(MediaRepository repository) {
        this.repository = repository;
    }
 
    public List<Media> findAll() {
        return repository.findAll();
    }
 
    public Optional<Media> findById(UUID id) {
        return repository.findById(id);
    }
 
    public List<Media> search(String search) {
        if (search == null || search.isBlank()) {
            return repository.findAll();
        }
        return repository.findByTitleContainingIgnoreCase(search);
    }
 
    public Media createMedia(Media media) {
        return repository.save(media);
    }
}