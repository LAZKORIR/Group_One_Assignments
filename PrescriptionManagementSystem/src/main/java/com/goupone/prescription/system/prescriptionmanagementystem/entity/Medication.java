package com.goupone.prescription.system.prescriptionmanagementystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unit;
    private String sideEffects;
    @Column(nullable = false)
    private boolean genericAvailable = false;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "medication_generics",
            joinColumns = @JoinColumn(name = "medication_id"),
            inverseJoinColumns = @JoinColumn(name = "generic_id")
    )
    private List<Medication> genericSubstitutes = new ArrayList<>();

    public  Medication(String name,String unit,String sideEffects,boolean genericAvailable){
        this.name=name;
        this.unit=unit;
        this.sideEffects=sideEffects;
        this.genericAvailable=genericAvailable;
    }

    // Getters and Setters
    public List<Medication> getGenericSubstitutes() {
        return genericSubstitutes;
    }

    public void setGenericSubstitutes(List<Medication> genericSubstitutes) {
        this.genericSubstitutes = genericSubstitutes;
    }

}



