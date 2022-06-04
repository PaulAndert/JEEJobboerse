package de.thb.jee.authexample.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "kompetenzen")
@Builder
@ToString
@Table(name = "kompetenzen")
public class KompetenzenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String beschreibung;

    @ManyToMany(mappedBy = "userKompetenzen")
    private List<UserEntity> KompetenzUsers ;

    @ManyToMany(mappedBy = "offeneStelleKompetenzen")
    private List<OffeneStellenEntity> KompetenzOffeneStellen;
}