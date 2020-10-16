package com.szalay.opencourtwebapp.db;

import com.szalay.opencourtwebapp.TextProcessor;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "hu_dontesek")
public class DecisionDto implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "caseNumber")
    public String caseNumber;

    @Column(name = "courtName")
    public String courtName;

    @Column(name = "caseType")
    public String caseType;

    @Column(name = "decisionText")
    public String decisionText;

    @Column(name = "decisionDate")
    public LocalDate decisionDate;

    @Column(name = "procedureYear")
    public String procedureYear;

    @Column(name = "subjectMatter")
    public String subjectMatter;

    @Column(name = "grammaticalKeywords")
    public String grammaticalKeywords;

    @Column(name = "frequentSearchKeywords")
    public String frequentSearchKeywords;

    @Column(name = "viewCount")
    public long viewCount;


    public DecisionDto() {
    }

    public DecisionDto(File file) {
        String fileName = file.getName();
        String processedDecisionText = TextProcessor.prepareDecisionText(file);
        this.caseNumber = TextProcessor.extractCaseNumber(fileName);
        this.courtName = TextProcessor.extractCourtName(this.decisionText);
        this.caseType = TextProcessor.extractCaseType(fileName);
        this.decisionText = processedDecisionText;
        this.procedureYear = TextProcessor.extractProcedureYear(fileName);
        this.subjectMatter = TextProcessor.extractSubjectMatter(this.decisionText);
        int[] dateArray = TextProcessor.extractDecisionDate((this.decisionText));
        this.decisionDate = LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);
        this.grammaticalKeywords = TextProcessor.extractGrammaticalKeywords(this.decisionText);
        this.frequentSearchKeywords = null;
        this.viewCount = 0;
    }

    public DecisionDto(String caseNumber, String courtName, String caseType, String decisionText, LocalDate decisionDate, String procedureYear, String subjectMatter, String grammaticalKeywords, String frequentSearchKeywords, long viewCount) {
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
