package com.dh.IntegradorBackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Domicilios")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String calle, localidad, provincia;
    @Column
    private int numero;


}
