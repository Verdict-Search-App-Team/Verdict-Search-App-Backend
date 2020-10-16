package com.szalay.opencourtwebapp;

public class DecisionObject {

    private String caseNumber;
    private String courtName;
    private String caseType;
    private String decisionText;
    private int[] decisionDate;
    private String procedureYear;
    private String subjectMatter;
    private String grammaticalKeywords;
    private String frequentSearchKeywords;
    long viewCount;

    public DecisionObject(String caseNumber, String courtName, String caseType, String decisionText, int[] decisionDate, String procedureYear, String subjectMatter, String grammaticalKeywords, String frequentSearchKeywords, long viewCount) {
        this.caseNumber = caseNumber;
        this.courtName = courtName;
        this.caseType = caseType;
        this.decisionText = decisionText;
        this.decisionDate = decisionDate;
        this.procedureYear = procedureYear;
        this.subjectMatter = subjectMatter;
        this.grammaticalKeywords = grammaticalKeywords;
        this.frequentSearchKeywords = frequentSearchKeywords;
        this.viewCount = viewCount;
    }

}
