package com.goupone.prescription.system.prescriptionmanagementystem.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
public class Physician extends User {

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Prescription> prescriptions;

    // Getters and Setters
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
