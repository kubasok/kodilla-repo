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
        TrelloBoardDto testBoardDto = testBoardDtoList.get(0);
        TrelloBoard testBoard = testBoardList.get(1);

        //Then
        assertEquals(3, testBoardDtoList.size());
        assertEquals(3, testBoardList.size());

        assertEquals("1", testBoardDto.getId());
        assertEquals("test", testBoardDto.getName());
        assertEquals("1", testBoardDto.getLists().get(0).getId());
        assertEquals("new list", testBoardDto.getLists().get(0).getName());
        assertFalse(testBoardDto.getLists().get(0).isClosed());

        assertEquals("1", testBoard.getId());
        assertEquals("trello todo", testBoard.getName());
        assertEquals("1", testBoard.getLists().get(0).getId());
        assertEquals("new list", testBoard.getLists().get(0).getName());
        assertFalse(testBoard.getLists().get(0).isClosed());


    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        List<TrelloListDto> trelloListDtos = new ArrayList<>();

        trelloLists.add(new TrelloList("1", "new list", false));
        trelloLists.add(new TrelloList("2", "one more list", false));
        trelloLists.add(new TrelloList("3", "hidden list", false));

        trelloListDtos.add(new TrelloListDto("1", "new list", false));
        trelloListDtos.add(new TrelloListDto("2", "one more list", false));
        trelloListDtos.add(new TrelloListDto("3","hidden list", false));

        //When
        List<TrelloListDto> testTrelloLists = trelloMapper.mapToListDto(trelloLists);
        List<TrelloList> testTrelloListDtos = trelloMapper.mapToList(trelloListDtos);
        TrelloListDto testTrelloListDto = testTrelloLists.get(0);
        TrelloList testTrelloList = testTrelloListDtos.get(2);
        //Then
        Assert.assertEquals(3, testTrelloListDtos.size());
        Assert.assertEquals(3, testTrelloLists.size());

        assertEquals("1", testTrelloListDto.getId());
        assertEquals("new list", testTrelloListDto.getName());
        assertFalse(testTrelloListDto.isClosed());

        assertEquals("3", testTrelloList.getId());
        assertEquals("hidden list", testTrelloList.getName());
        assertTrue(testTrelloList.isClosed());
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