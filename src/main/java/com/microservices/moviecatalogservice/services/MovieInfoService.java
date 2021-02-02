package com.microservices.moviecatalogservice.services;

import com.microservices.moviecatalogservice.models.Movie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getDefaultMovieInfo")
    public Movie getMovieInfo(String movieId) {
        return restTemplate.getForObject("http://movie-info-service/movie-info/movie/" + movieId, Movie.class);
    }

    private Movie getDefaultMovieInfo(String movieId) {
        return Movie.builder()
                .movieId("X")
                .movieName("X")
                .description("X")
                .build();
    }
}
