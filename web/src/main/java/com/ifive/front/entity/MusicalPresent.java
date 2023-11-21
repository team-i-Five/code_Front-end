package com.ifive.front.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifive.front.dto.MusicalPresentDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString(of = {"title", "musicalId", "posterUrl"}, includeFieldNames = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "musical_present")
public class MusicalPresent {
    @Id
    @Column
    private Integer idNum;

    @Column
    private Integer musicalId;

    @Column
    private String title;

    @Column
    private String posterUrl;

    @Column
    private String genre;

    @Column
    private String date;

    @Column
    private String startDate;

    @Column
    private String endDate;

    @Column
    private String location;

    @Column
    private String actors;

    @Column
    private String ageRating;

    @Column
    private String runningTime;

    @Column
    private String info;

    @Column
    private String synopsis;

    @Column
    private String synopsisClear;

    @Column
    private String tokenizedData;

    @Column
    private byte[] synopsisVector;

    @Column
    private byte[] synopsisNumpy;

    @Column
    private byte[] synopsisNumpyScale;

    @Column
    private String tag1;

    @Column
    private String tag2;
    
    @Column
    private String tag3;

    public MusicalPresentDTO toDTO() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, MusicalPresentDTO.class);
    }
}