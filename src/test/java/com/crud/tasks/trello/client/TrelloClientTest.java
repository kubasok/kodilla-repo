package com.crud.tasks.trello.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {
    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://api.trello.com/1");
        when(trelloConfig.getTrelloAppKey()).thenReturn("87e7e9bdaf61fe95b39fbae37465bbf6");
        when(trelloConfig.getTrelloToken()).thenReturn("83f4c73d81a9eb384ab95e3792af56e7b3ca6bfe72bbed1b4645aff473235925");
        when(trelloConfig.getTrelloUsername()).thenReturn("kubasok");
//        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
//        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
//        when(trelloConfig.getTrelloToken()).thenReturn("test");
//        when(trelloConfig.getTrelloUsername()).thenReturn("kubasok");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
//        Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/kubasok/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

//        When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();



//        Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
//        Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test Task",
                "Test Description",
                "top",
                "test_id"
        );


        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20Task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com",
                new BadgesDto("0",new AttachmentsByTypeDto(new TrelloDto("0","0")))
        );

    when(restTemplate.postForObject(uri,null,CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);


//        When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

//        Then
        assertEquals("1",newCard.getId());
        assertEquals("Test task",newCard.getName());
        assertEquals("http://test.com",newCard.getShortUrl());

    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
//        Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/kubasok/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);

//        When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

//        Then
        assertTrue(fetchedTrelloBoards.isEmpty());

    }

    @Test
    public void shouldPrintUrl() {
        List<TrelloBoardDto> testList = trelloClient.getTrelloBoards();

        Assert.assertEquals(0, testList.size());
        testList.stream().forEach(System.out::println);
//        URI url = trelloClient.getUrl(trelloConfig.getTrelloApiEndpoint(), trelloConfig.getTrelloAppKey(),
//                trelloConfig.getTrelloToken(),"name,id",trelloConfig.getTrelloUsername());
//
//        System.out.println(url.toString());
    }
}