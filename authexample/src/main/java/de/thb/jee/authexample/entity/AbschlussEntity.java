package de.thb.jee.authexample.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "abschluss")
@Builder
@ToString
@Table(name = "abschluss")
public class AbschlussEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String beschreibung;

    @ManyToMany(mappedBy = "userAbschluesse", fetch = FetchType.LAZY)
    private List<UserEntity> abschlussUsers;


}