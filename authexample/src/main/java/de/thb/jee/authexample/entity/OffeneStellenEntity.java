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
@Entity(name = "offene_stelle")
@Builder
@ToString
@Table(name = "offene_stelle")
public class OffeneStellenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String beschreibung;

    private String gehalt;

    private int userId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "offene_stelle_besitzt_kompetenz",
            joinColumns = {
                    @JoinColumn(name = "offene_stelle_id", referencedColumnName = "id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "kompetenz_id", referencedColumnName = "id", nullable = false, updatable = false)})
    private List<KompetenzenEntity> offeneStelleKompetenzen ;
}
