package de.thb.jee.authexample.entity;

import lombok.NoArgsConstructor;

import lombok.AccessLevel;
import lombok.Data;


@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class DataTransfer {
    private String eingabe;
    private int roleId;
}
