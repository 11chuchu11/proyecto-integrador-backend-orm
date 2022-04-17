package com.dh.IntegradorBackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String nombre,apellido;
    @Column
    private int matricula;

    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY)
    private Set<Turno> turnos= new HashSet<>();

    public Odontologo() {
    }

}
