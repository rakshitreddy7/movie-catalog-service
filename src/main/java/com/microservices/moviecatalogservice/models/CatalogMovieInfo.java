package com.microservices.moviecatalogservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogMovieInfo {
    private String movieId;
    private String movieName;
    private String description;
    private double rating;
}
