package com.exemple.dslist.services;

import com.exemple.dslist.dto.GameListDTO;
import com.exemple.dslist.entities.GameList;
import com.exemple.dslist.projection.GameMinProjection;
import com.exemple.dslist.repositories.GameListRepository;
import com.exemple.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll(){
        List<GameList> result = gameListRepository.findAll();
        return result.stream().map(x -> new GameListDTO(x)).toList();
    }

    @Transactional
    public void move(Long listId, int sourceIndex, int destinationIndex){

        List<GameMinProjection> result = gameRepository.searchByList(listId);

        GameMinProjection obj = result.remove(sourceIndex);
        result.add(destinationIndex, obj);

        int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;
        int max = sourceIndex < destinationIndex ? destinationIndex : sourceIndex;

        for (int i = min; i <= max; i++){
            gameListRepository.updateBelongingPosition(listId, result.get(i).getId(), i);
        }
    }
}
