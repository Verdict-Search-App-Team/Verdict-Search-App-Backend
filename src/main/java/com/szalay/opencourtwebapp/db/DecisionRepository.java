package com.szalay.opencourtwebapp.db;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource
public interface DecisionRepository extends CrudRepository<DecisionDto, String> {

    List<DecisionDto> findByCaseNumber(String query);
    List<DecisionDto> findByDecisionTextContaining(String query);

    List<DecisionDto> findByFrequentSearchKeywordsContainingOrderByViewCountDesc(String query);
    List<DecisionDto> findByDecisionTextContainingOrderByViewCountDesc(String query);
    List<DecisionDto> findByViewCountGreaterThanEqualOrderByViewCountDesc(long minimumCount);


    @Modifying
    @Transactional
    @Query("update DecisionDto u set u.frequentSearchKeywords = ?1 where u.caseNumber = ?2")
    int setFrequentSearchKeywordsFor(String searchedTerm, String caseNumberOfCurrentDecision);

    @Modifying
    @Transactional
    @Query("update DecisionDto u set u.viewCount = ?1 where u.caseNumber = ?2")
    int setViewCountsFor(long newViewCount, String caseNumberOfCurrentDecision);

}
