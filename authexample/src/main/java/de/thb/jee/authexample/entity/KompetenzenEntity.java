package de.thb.jee.authexample.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Kompetenzen")
@Builder
@ToString
public class KompetenzenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String beschreibung;

    @ManyToMany(mappedBy = "UserEntity")
    private List<UserEntity> users ;
}