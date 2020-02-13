package com.szalay.opencourtwebapp.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DecisionRepository extends CrudRepository<DecisionDto, String> {

    List<DecisionDto> findByBirosagneveContaining(String query);
    List<DecisionDto> findByBirosagneve(String query);
    List<DecisionDto> findByUgyszam(String query);
    List<DecisionDto> findByTargy(String query);

}
