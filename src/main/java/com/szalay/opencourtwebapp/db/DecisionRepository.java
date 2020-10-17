package com.szalay.opencourtwebapp.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface DecisionRepository extends CrudRepository<DecisionDto, String> {

    List<DecisionDto> findByCaseNumber(String query);
    List<DecisionDto> findByCaseNumberContaining(String query);
    List<DecisionDto> findByDecisionTextContaining(String query);


}
