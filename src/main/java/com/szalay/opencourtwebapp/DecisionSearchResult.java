package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;

class DecisionSearchResult {

    DecisionDto decisionDto;
    String keresettSzovegResz;

    public DecisionSearchResult(DecisionDto decisionDto, String keresettSzovegResz) {
        this.decisionDto = decisionDto;
        this.keresettSzovegResz = keresettSzovegResz;
    }
}
