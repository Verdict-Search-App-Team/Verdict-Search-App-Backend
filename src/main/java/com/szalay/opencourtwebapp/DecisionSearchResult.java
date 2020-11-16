package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;

public class DecisionSearchResult {

    final DecisionDto decisionDto;
    final String searchContextString;
    final String searchedTerm;

    public DecisionSearchResult(DecisionDto decisionDto, String searchContextString, String searchedTerm) {
        this.decisionDto = decisionDto;
        this.searchContextString = searchContextString;
        this.searchedTerm = searchedTerm;
    }

}
