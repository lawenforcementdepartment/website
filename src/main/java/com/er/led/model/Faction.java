package com.er.led.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

//    LED,
//    FDC,
//    VI,
//    MOTB,
//    BOS,
//    CMG,
//    GOM,
//    EC,
//    CIV,
//    GIS,
//    AC,
//    AE

@Entity
@Data
public class Faction implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private String acronym;

    private Relationship relationship;
}
