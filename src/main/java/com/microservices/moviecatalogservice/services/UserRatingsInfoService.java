package com.microservices.moviecatalogservice.services;

import com.microservices.moviecatalogservice.models.Rating;
import com.microservices.moviecatalogservice.models.UserRatings;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class UserRatingsInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getDefaultUserRatingsInfo")
    public UserRatings getUserRatingsInfo(String id) {
        return restTemplate.getForObject("http://ratings-data-service/ratings/user/" + id, UserRatings.class);
    }

    public UserRatings getDefaultUserRatingsInfo(String id) {
        List<Rating> ratings = Collections.singletonList(Rating.builder().movieId("x").rating(0).build());

        return UserRatings.builder()
                .ratings(ratings)
                .build();
    }
}
