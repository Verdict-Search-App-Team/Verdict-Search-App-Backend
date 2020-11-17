package com.szalay.opencourtwebapp.db;

import com.szalay.opencourtwebapp.IOUtils;
import com.szalay.opencourtwebapp.TextProcessor;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;

@Entity
@Table(name = "hu_dontesek")
public class DecisionDto {

    @Id
    @Column
    public String caseNumber;

    @Column
    public String courtName;

    @Column
    public String caseType;

    @Lob
    @Column(length = 65000)
    public String decisionText;

    @Column
    public LocalDate decisionDate;

    @Column
    public String procedureYear;

    @Lob
    @Column(length = 65000)
    public String subjectMatter;

    @Column
    public String source;

    @Column
    public String grammaticalKeywords;

    @Column
    public String frequentSearchKeywords;

    @Column
    public long viewCount;

    public DecisionDto() {
        this.caseNumber = null;
        this.courtName = null;
        this.caseType = null;
        this.decisionText = null;
        this.decisionDate = null;
        this.procedureYear = null;
        this.subjectMatter = null;
        this.grammaticalKeywords = null;
        this.frequentSearchKeywords = null;
        this.viewCount = -1;
        this.source = null;
    }

    public DecisionDto(File file) {
        String rawtext = IOUtils.readRtf(file.getAbsolutePath());
        this.caseNumber = TextProcessor.extractCaseNumber(file.getName());
        if (rawtext != null) {
            this.decisionText = TextProcessor.prepareDecisionText(rawtext);
            System.out.println("The length of decisiontext is: " + this.decisionText.length());
            System.out.println(this.decisionText);
            this.courtName = TextProcessor.extractCourtName(this.decisionText);
            this.caseType = TextProcessor.extractCaseType(file.getName());
            this.procedureYear = TextProcessor.extractProcedureYear(file.getName());
            this.subjectMatter = TextProcessor.extractSubjectMatter(this.decisionText);
            int[] dateArray = TextProcessor.extractDecisionDate(this.decisionText);
            this.decisionDate = LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);
            this.grammaticalKeywords = TextProcessor.extractGrammaticalKeywords(this.decisionText);
            this.frequentSearchKeywords = null;
            this.viewCount = 0;
            this.source = TextProcessor.reconstructSource(file, this.courtName, this.procedureYear);
        }
    }

    public DecisionDto(String caseNumber, String courtName, String caseType, String decisionText, LocalDate decisionDate,
                       String procedureYear, String subjectMatter, String grammaticalKeywords, String frequentSearchKeywords,
                       long viewCount) {
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
        this.source = null;
    }

}

