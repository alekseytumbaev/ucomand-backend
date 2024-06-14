package com.example.ucomandbackend.city;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Table(name = "cities")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(innerTypeName = "F")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
}
