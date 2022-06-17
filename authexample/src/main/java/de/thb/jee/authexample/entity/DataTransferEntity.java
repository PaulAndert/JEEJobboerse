package de.thb.jee.authexample.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.AccessLevel;
import lombok.Data;


@Data
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class DataTransferEntity {
    private int roleId;
    private String beschreibung;
    private String kompetenz;
    private String abschluss;
    private float gehalt;

}
