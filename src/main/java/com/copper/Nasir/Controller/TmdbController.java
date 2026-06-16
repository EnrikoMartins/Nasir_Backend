package com.copper.Nasir.Controller;

import com.copper.Nasir.DTO.CardResponseDTO;
import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Service.TmdbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tmdb")
public class TmdbController {

    private final TmdbService tmdbService;

    public TmdbController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    // GET /api/tmdb/search/filmes?q=batman
    @GetMapping("/search/filmes")
    public ResponseEntity<Map> searchMovies(@RequestParam String q) {
        return ResponseEntity.ok(tmdbService.searchMovies(q));
    }

    // GET /api/tmdb/search/series?q=breaking+bad
    @GetMapping("/search/series")
    public ResponseEntity<Map> searchSeries(@RequestParam String q) {
        return ResponseEntity.ok(tmdbService.searchSeries(q));
    }

    // POST /api/tmdb/import/filme/550
    @PostMapping("/import/filme/{tmdbId}")
    public ResponseEntity<CardResponseDTO> importMovie(@PathVariable int tmdbId) {
        Card card = tmdbService.importMovie(tmdbId);
        return ResponseEntity.ok(CardResponseDTO.fromEntity(card));
    }

    // POST /api/tmdb/import/serie/1396
    @PostMapping("/import/serie/{tmdbId}")
    public ResponseEntity<CardResponseDTO> importSeries(@PathVariable int tmdbId) {
        Card card = tmdbService.importSeries(tmdbId);
        return ResponseEntity.ok(CardResponseDTO.fromEntity(card));
    }
}