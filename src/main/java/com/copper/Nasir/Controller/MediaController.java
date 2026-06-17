package com.copper.Nasir.Controller;
 
import com.copper.Nasir.Entity.Media;
import com.copper.Nasir.Service.MediaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.UUID;
 
@RestController
@RequestMapping("/medias")
public class MediaController {
 
    private final MediaService service;
 
    @Autowired
    public MediaController(MediaService service) {
        this.service = service;
    }
 
    // GET /medias            -> lista tudo
    // GET /medias?search=abc -> filtra por título contendo "abc" (ignora maiúsculas/minúsculas)
    @GetMapping
    public ResponseEntity<List<Media>> getMedias(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(service.search(search));
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Media> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
 
    @PostMapping
    public ResponseEntity<Media> createMedia(@Valid @RequestBody Media media) {
        Media created = service.createMedia(media);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
