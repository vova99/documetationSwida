package com.swida.documetation.data.entity.subObjects;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BreedOfTreeDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String description;
}