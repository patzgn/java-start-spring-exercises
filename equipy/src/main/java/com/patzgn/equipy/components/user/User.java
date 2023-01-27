package com.patzgn.equipy.components.user;

import com.patzgn.equipy.components.assignment.Assignment;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String pesel;
    @OneToMany(mappedBy = "user")
    private List<Assignment> assignments = new ArrayList<>();

}
