package com.microservices.moviecatalogservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Catalog {
    private String userId;
    private List<CatalogMovieInfo> catalogMovieInfos;
}
