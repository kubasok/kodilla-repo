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
        Assert.assertEquals(3, testBoardDtoList.size());
        Assert.assertEquals(3, testBoardList.size());
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
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card 1", "New card 1", "1", "1a");
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card 2", "New card 1", "1", "1a");

        //When
        TrelloCardDto testTrelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        TrelloCard testTrelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        Assert.assertEquals("Card 1", testTrelloCardDto.getName());
        Assert.assertEquals("Card 2", testTrelloCard.getName());
    }
}