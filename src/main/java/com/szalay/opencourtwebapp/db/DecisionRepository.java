package com.szalay.opencourtwebapp.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DecisionRepository extends CrudRepository<DecisionDto, String> {

    List<DecisionDto> findByUgyszam(String query);

    /*
    Search methods
     */
    List<DecisionDto> findByUgyszamContaining(String query);
    List<DecisionDto> findByBirosagContaining(String query);
    List<DecisionDto> findByUgytipusContaining(String query);
    List<DecisionDto> findByHatarozatszovegContaining(String query);
//    List<DecisionDto> findByhatarozatdatumaContaining(String query);
//    List<DecisionDto> findByEljaraseveContaining(String query);
//    List<DecisionDto> findByTargyContaining(String query);
//    List<DecisionDto> findByKulcsszavaknyelvtaniContaining(String query);
//    List<DecisionDto> findByKulcsszavakkeresesContaining(String query);
//    List<DecisionDto> findByMegtekintesekszamaContaining(String query);
//    List<DecisionDto> findByKiemeltszovegContaining(String query);
//    List<DecisionDto> findByMegjegyzesekContaining(String query);


}
