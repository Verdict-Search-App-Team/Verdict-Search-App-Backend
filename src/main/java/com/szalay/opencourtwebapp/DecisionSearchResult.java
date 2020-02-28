package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;

class DecisionSearchResult {

    DecisionDto decisionDto;
    String searchContextString;
    String searchedTerm;

    public DecisionSearchResult(DecisionDto decisionDto, String searchContextString, String searchedTerm) {
        this.decisionDto = decisionDto;
        this.searchContextString = searchContextString;
        this.searchedTerm = searchedTerm;
    }

}
