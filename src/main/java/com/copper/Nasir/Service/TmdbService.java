package com.copper.Nasir.Service;

import com.copper.Nasir.Config.TmdbConfig;
import com.copper.Nasir.DTO.CardRequestDTO;
import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Enum.CardCategory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class TmdbService {

    private final RestTemplate restTemplate;
    private final TmdbConfig tmdbConfig;
    private final CardService cardService;

    public TmdbService(RestTemplate restTemplate, TmdbConfig tmdbConfig, CardService cardService) {
        this.restTemplate = restTemplate;
        this.tmdbConfig = tmdbConfig;
        this.cardService = cardService;
    }

    // Monta o header com o Bearer Token
    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tmdbConfig.getBearerToken());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    // Busca filmes por nome
    public Map searchMovies(String query) {
        String url = tmdbConfig.getBaseUrl() + "/search/movie?query=" + query + "&language=pt-BR";
        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }

    // Busca séries por nome
    public Map searchSeries(String query) {
        String url = tmdbConfig.getBaseUrl() + "/search/tv?query=" + query + "&language=pt-BR";
        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }

    // Importa um filme do TMDB direto para o banco local
    public Card importMovie(int tmdbId) {
        String url = tmdbConfig.getBaseUrl() + "/movie/" + tmdbId + "?language=pt-BR";
        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> data = response.getBody();

        CardRequestDTO dto = new CardRequestDTO(
                (String) data.get("title"),
                tmdbConfig.getImageBaseUrl() + data.get("poster_path"),
                data.get("vote_average") != null ? ((Number) data.get("vote_average")).doubleValue() : 0.0,
                (String) data.get("release_date"),
                CardCategory.filmes,
                (String) data.get("overview")
        );

        return cardService.createCard(dto);
    }

    // Importa uma série do TMDB direto para o banco local
    public Card importSeries(int tmdbId) {
        String url = tmdbConfig.getBaseUrl() + "/tv/" + tmdbId + "?language=pt-BR";
        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> data = response.getBody();

        CardRequestDTO dto = new CardRequestDTO(
                (String) data.get("name"),
                tmdbConfig.getImageBaseUrl() + data.get("poster_path"),
                data.get("vote_average") != null ? ((Number) data.get("vote_average")).doubleValue() : 0.0,
                (String) data.get("first_air_date"),
                CardCategory.series,
                (String) data.get("overview")
        );

        return cardService.createCard(dto);
    }
}