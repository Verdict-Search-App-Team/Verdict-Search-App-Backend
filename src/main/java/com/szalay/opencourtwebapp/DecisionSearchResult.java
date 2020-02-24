package com.szalay.opencourtwebapp;

import com.szalay.opencourtwebapp.db.DecisionDto;

public class DecisionSearchResult {

    public DecisionDto decisionDto;
    public String keresettSzovegResz;

    public DecisionSearchResult(DecisionDto decisionDto, String keresettSzovegResz) {
        this.decisionDto = decisionDto;
        this.keresettSzovegResz = keresettSzovegResz;
    }
}
