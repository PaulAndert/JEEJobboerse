package de.thb.jee.authexample.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Offene Stellen")
@Builder
@ToString
public class OffeneStellenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String beschreibung;

    private String gehalt;

    private int userId;

    @ManyToMany(mappedBy = "UserEntity")
    private List<UserEntity> users;
}
