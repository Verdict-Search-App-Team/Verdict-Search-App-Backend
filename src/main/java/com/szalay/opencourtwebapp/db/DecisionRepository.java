package com.szalay.opencourtwebapp.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DecisionRepository extends CrudRepository<DecisionDto, String> {

    List<DecisionDto> findByUgyszam(String query);

    /*
    Search methods
     */
    List<DecisionDto> findByUgyszamContaining(String query);
    List<DecisionDto> findByBevezetoContaining(String query);
    List<DecisionDto> findByRendelkezoContaining(String query);
    List<DecisionDto> findByTenyallasContaining(String query);
    List<DecisionDto> findByJogiindokolasContaining(String query);
    List<DecisionDto> findByZaroContaining(String query);
    List<DecisionDto> findByBirosagneveContaining(String query);
    List<DecisionDto> findByUgyTipusContaining(String query);
    List<DecisionDto> findByEljarasTipusContaining(String query);
    List<DecisionDto> findByEljarasSzakaszContaining(String query);
    List<DecisionDto> findByTargyContaining(String query);
    List<DecisionDto> findByDontesContaining(String query);
    List<DecisionDto> findByDontesmasodfokContaining(String query);
    List<DecisionDto> findByDonteselsofokContaining(String query);
    List<DecisionDto> findByHatarozatStringCleanContaining(String query);

}
