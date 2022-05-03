package com.sovtech.test.graphql.server.wrapper.datafetcher;

import com.sovtech.test.graphql.server.model.Person;
import com.sovtech.test.graphql.server.model.SwapAPI;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GraphQLDataFetcher {
    private final String REST_URL = "https://swapi.dev/api/people";

    @Autowired
    private RestTemplate restTemplate;

    public DataFetcher<SwapAPI> getSwapAP() {
        return dataFetchingEnvironment -> {
            return restTemplate.getForObject(REST_URL, SwapAPI.class);
        };
    }
    public DataFetcher<Person> getPerson() {
        return dataFetchingEnvironment -> {
            return restTemplate.getForObject(REST_URL + "/1/", Person.class);
        };
    }
    public DataFetcher<SwapAPI> getPersonByPage() {
        return dataFetchingEnvironment -> {
            return restTemplate.getForObject(REST_URL + "/?page=" + dataFetchingEnvironment.getArgument("page"), SwapAPI.class);
        };
    }
    public DataFetcher<SwapAPI> getPersonByName() {
        return dataFetchingEnvironment -> {
            return restTemplate.getForObject(REST_URL + "/?search=" + dataFetchingEnvironment.getArgument("name"), SwapAPI.class);
        };
    }
}