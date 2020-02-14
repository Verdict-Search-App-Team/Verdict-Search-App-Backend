package com.szalay.opencourtwebapp.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DecisionRepository extends CrudRepository<DecisionDto, String> {

    List<DecisionDto> findByBirosagneveContaining(String query);
    List<DecisionDto> findByBirosagneve(String query);
    List<DecisionDto> findByUgyszam(String query);
    List<DecisionDto> findByTargyContaining(String query);
    List<DecisionDto> findByBevezetoContaining(String query);
    List<DecisionDto> findByRendelkezoContaining(String query);
    List<DecisionDto> findByTenyallasContaining(String query);
    List<DecisionDto> findByJogiindokolasContaining(String query);
    List<DecisionDto> findByHatarozatStringCleanContaining(String query);

}
