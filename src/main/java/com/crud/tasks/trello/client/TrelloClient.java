package com.crud.tasks.trello.client;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value ("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value ("${trello.app.key}")
    private String trelloAppKey;

    @Value ("${trello.app.token}")
    private String trelloToken;

    @Value ("${trello.app.username}")
    private String trelloUsername;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TaskMapper taskMapper;

    public List<TrelloBoardDto> getTrelloBoards() throws TaskNotFoundException {
        URI url = getUrl(trelloApiEndpoint, trelloAppKey,trelloToken,"name,id",trelloUsername);

        Optional<TrelloBoardDto[]> boardsResponse =
                Optional.of(restTemplate.getForObject(url, TrelloBoardDto[].class));

        return Arrays.asList(boardsResponse.orElseThrow(TaskNotFoundException::new));
    }


    private URI getUrl(String endpoint, String key, String token, String fields, String username) {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/members/" + username + "/boards")
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("fields", fields)
                .build().encode().toUri();
    }
}
