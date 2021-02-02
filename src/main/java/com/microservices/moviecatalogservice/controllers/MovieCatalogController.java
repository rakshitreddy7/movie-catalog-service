package com.microservices.moviecatalogservice.controllers;

import com.microservices.moviecatalogservice.models.Catalog;
import com.microservices.moviecatalogservice.models.CatalogMovieInfo;
import com.microservices.moviecatalogservice.models.Movie;
import com.microservices.moviecatalogservice.models.UserRatings;
import com.microservices.moviecatalogservice.services.MovieInfoService;
import com.microservices.moviecatalogservice.services.UserRatingsInfoService;
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

    @Autowired
    private MovieInfoService movieInfoService;

    @Autowired
    private UserRatingsInfoService userRatingsInfoService;

    @GetMapping(value = "/users/{userId}", produces = "application/json")
    public Catalog getUserCatalog(@PathVariable(value = "userId") String id) {

        List<CatalogMovieInfo> movies = new ArrayList<>();

        UserRatings userRatings = userRatingsInfoService.getUserRatingsInfo(id);

        if (userRatings == null)
            return Catalog.builder().build();

        userRatings.getRatings().forEach(rating -> {
            String movieId = rating.getMovieId();
            double movieRating = rating.getRating();

            Movie movie = movieInfoService.getMovieInfo(movieId);

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
