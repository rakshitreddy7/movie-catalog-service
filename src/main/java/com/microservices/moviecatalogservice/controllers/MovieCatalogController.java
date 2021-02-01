package com.microservices.moviecatalogservice.controllers;

import com.microservices.moviecatalogservice.models.Catalog;
import com.microservices.moviecatalogservice.models.CatalogMovieInfo;
import com.microservices.moviecatalogservice.models.Movie;
import com.microservices.moviecatalogservice.models.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/users/{userId}")
    public Catalog getUserCatalog(@PathVariable(value = "userId") String id) {

        List<CatalogMovieInfo> movies = new ArrayList<>();

        UserRatings userRatings = restTemplate.getForObject("http://localhost:8082/ratings/user/" + id, UserRatings.class);

        if (userRatings == null)
            return Catalog.builder().build();

        userRatings.getRatings().forEach(rating -> {
            String movieId = rating.getMovieId();
            double movieRating = rating.getRating();

            Movie movie = restTemplate.getForObject("http://localhost:8083/movie-info/movie/" + movieId, Movie.class);

            if (movie != null) {
                movies.add(CatalogMovieInfo.builder()
                        .movieId(movieId)
                        .movieName(movie.getMovieName())
                        .description(movie.getDescription())
                        .rating(movieRating)
                        .build());
            }
        });

        return Catalog.builder()
                .userId(id)
                .catalogMovieInfos(movies)
                .build();
    }

}
