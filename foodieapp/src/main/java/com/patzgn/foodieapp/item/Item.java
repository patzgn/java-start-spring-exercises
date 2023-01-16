package com.patzgn.foodieapp.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private String name;
    private String shortDescription;
    @Column(length = 1024)
    private String description;
    private String imgUrl;

}
