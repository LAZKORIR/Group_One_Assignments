package com.goupone.prescription.system.prescriptionmanagementystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Physician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One physician can write many prescriptionEntities
    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PrescriptionEntity> prescriptionEntities = new ArrayList<>();


    public List<PrescriptionEntity> getPrescriptionEntities() { return prescriptionEntities; }
    public void setPrescriptionEntities(List<PrescriptionEntity> prescriptionEntities) { this.prescriptionEntities = prescriptionEntities; }
}



