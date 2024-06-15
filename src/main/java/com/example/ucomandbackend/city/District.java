package com.example.ucomandbackend.city;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Table(name = "districts")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(innerTypeName = "F")
public class District {

    @Id
    private Long id;

    String name;
}
