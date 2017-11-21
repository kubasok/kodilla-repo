package com.crud.tasks.service;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {
    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;


    @Test
    public void fetchTrelloBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtoList;

        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "my_task", trelloLists));

        //When
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);

        List<TrelloBoardDto> testTrelloList = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(1, testTrelloList.size());
        assertEquals("1", testTrelloList.get(0).getId());
        assertEquals("my_task", testTrelloList.get(0).getName());
        assertEquals(trelloLists, testTrelloList.get(0).getLists());

    }

    @Test
    public void createTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test_card", "Testing create card", "1", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Test",
                "http://test.com",new BadgesDto("1", new AttachmentsByTypeDto(new TrelloDto("1", "1"))));

        //When
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        CreatedTrelloCardDto testCreatedTrelloCardDto = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals(createdTrelloCardDto.getId(), testCreatedTrelloCardDto.getId());
        assertEquals(createdTrelloCardDto.getName(), testCreatedTrelloCardDto.getName());
        assertEquals(createdTrelloCardDto.getShortUrl(), testCreatedTrelloCardDto.getShortUrl());
    }

}