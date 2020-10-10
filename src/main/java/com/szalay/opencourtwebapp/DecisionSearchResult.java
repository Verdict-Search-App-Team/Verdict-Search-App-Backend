package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;

public class DecisionSearchResult {

    DecisionDto decisionDto;
    String searchContextString;
    String searchedTerm;

    public DecisionSearchResult(DecisionDto decisionDto, String searchContextString, String searchedTerm) {
        this.decisionDto = decisionDto;
        this.searchContextString = searchContextString;
        this.searchedTerm = searchedTerm;
    }

    public DecisionDto getDecisionDto() {
        return decisionDto;
    }

    public String getSearchContextString() {
        return searchContextString;
    }

    public String getSearchedTerm() {
        return searchedTerm;
    }
}
