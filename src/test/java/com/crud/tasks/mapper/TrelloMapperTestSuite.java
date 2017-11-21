package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        List<TrelloList> trelloLists = new ArrayList<>();
        List<TrelloListDto> trelloListDtos = new ArrayList<>();

        trelloLists.add(new TrelloList("1", "new list", false));
        trelloListDtos.add(new TrelloListDto("1", "new list", false));

        trelloBoardList.add(new TrelloBoard("1","test", trelloLists));
        trelloBoardList.add(new TrelloBoard("1","trello todo", trelloLists));
        trelloBoardList.add(new TrelloBoard("1","trello done", trelloLists));

        trelloBoardDtoList.add(new TrelloBoardDto("1","test", trelloListDtos));
        trelloBoardDtoList.add(new TrelloBoardDto("1","trello todo", trelloListDtos));
        trelloBoardDtoList.add(new TrelloBoardDto("1","trello done", trelloListDtos));

        //When
        List<TrelloBoardDto> testBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);
        List<TrelloBoard> testBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        assertEquals(3, testBoardDtoList.size());
        assertEquals(3, testBoardList.size());

        assertEquals("1", testBoardDtoList.get(0).getId());
        assertEquals("test", testBoardDtoList.get(0).getName());
        assertEquals("1", testBoardDtoList.get(0).getLists().get(0).getId());
        assertEquals("new list", testBoardDtoList.get(0).getLists().get(0).getName());
        assertFalse(testBoardDtoList.get(0).getLists().get(0).isClosed());

        assertEquals("1", testBoardList.get(1).getId());
        assertEquals("trello todo", testBoardList.get(1).getName());
        assertEquals("1", testBoardList.get(1).getLists().get(0).getId());
        assertEquals("new list", testBoardList.get(1).getLists().get(0).getName());
        assertFalse(testBoardList.get(0).getLists().get(0).isClosed());


    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        List<TrelloListDto> trelloListDtos = new ArrayList<>();

        trelloLists.add(new TrelloList("1", "new list", false));
        trelloLists.add(new TrelloList("2", "one more list", false));
        trelloLists.add(new TrelloList("3", "hidden list", true));

        trelloListDtos.add(new TrelloListDto("1", "new list", false));
        trelloListDtos.add(new TrelloListDto("2", "one more list", false));
        trelloListDtos.add(new TrelloListDto("3","hidden list", true));

        //When
        List<TrelloListDto> testTrelloLists = trelloMapper.mapToListDto(trelloLists);
        List<TrelloList> testTrelloListDtos = trelloMapper.mapToList(trelloListDtos);

        //Then
        Assert.assertEquals(3, testTrelloListDtos.size());
        Assert.assertEquals(3, testTrelloLists.size());

        assertEquals("1", testTrelloLists.get(0).getId());
        assertEquals("new list", testTrelloLists.get(0).getName());
        assertFalse(testTrelloLists.get(0).isClosed());

        assertEquals("3", testTrelloListDtos.get(2).getId());
        assertEquals("hidden list", testTrelloListDtos.get(2).getName());
        assertTrue(testTrelloListDtos.get(2).isClosed());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card 1", "New card 1", "1", "1a");
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card 2", "New card 2", "2", "2b");

        //When
        TrelloCardDto testTrelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        TrelloCard testTrelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        Assert.assertEquals("Card 1", testTrelloCardDto.getName());
        Assert.assertEquals("New card 1", testTrelloCardDto.getDescription());
        Assert.assertEquals("1", testTrelloCardDto.getPos());
        Assert.assertEquals("1a", testTrelloCardDto.getListId());


        Assert.assertEquals("Card 2", testTrelloCard.getName());
        Assert.assertEquals("New card 2", testTrelloCard.getDescription());
        Assert.assertEquals("2", testTrelloCard.getPos());
        Assert.assertEquals("2b", testTrelloCard.getListId());
    }
}