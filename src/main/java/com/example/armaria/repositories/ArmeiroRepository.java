package com.example.armaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.Armeiro;

public interface ArmeiroRepository extends JpaRepository<Armeiro, String> {

}
