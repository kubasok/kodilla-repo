package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloControllerTestSuite {
    @InjectMocks
    private TrelloController trelloController;

    @Mock
    private TrelloFacade trelloFacade;

    @Test
    public void testGetTrelloBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtoList;

        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        //When
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        try {
            trelloBoardDtoList = trelloController.getTrelloBoards();
        } catch (TaskNotFoundException e) {
            trelloBoardDtoList = new ArrayList<>();
        }

        //Then
        assertEquals(1, trelloBoardDtoList.size());
        assertEquals("1", trelloBoardDtoList.get(0).getId());
        assertEquals("my_task", trelloBoardDtoList.get(0).getName());
        assertEquals(trelloLists, trelloBoardDtoList.get(0).getLists());
    }

    @Test
    public void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test_card", "Testing create card", "1", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Test",
                "http://test.com",new BadgesDto("1", new AttachmentsByTypeDto(new TrelloDto("1", "1"))));

        //When
        when(trelloFacade.createCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        CreatedTrelloCardDto testCreatedTrelloCardDto = trelloController.createTrelloCard(trelloCardDto);

        //Then
        assertEquals(createdTrelloCardDto.getId(), testCreatedTrelloCardDto.getId());
        assertEquals(createdTrelloCardDto.getName(), testCreatedTrelloCardDto.getName());
        assertEquals(createdTrelloCardDto.getShortUrl(), testCreatedTrelloCardDto.getShortUrl());

    }

}