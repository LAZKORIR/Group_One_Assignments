package com.goupone.prescription.system.prescriptionmanagementystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Physician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One physician can write many prescriptionEntities
    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PrescriptionEntity> prescriptionEntities = new ArrayList<>();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<PrescriptionEntity> getPrescriptionEntities() { return prescriptionEntities; }
    public void setPrescriptionEntities(List<PrescriptionEntity> prescriptionEntities) { this.prescriptionEntities = prescriptionEntities; }
}



