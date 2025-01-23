package com.exemple.dslist.repositories;

import com.exemple.dslist.entities.Game;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
