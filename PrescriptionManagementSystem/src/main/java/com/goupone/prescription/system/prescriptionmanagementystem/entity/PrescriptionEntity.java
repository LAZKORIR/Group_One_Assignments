package com.goupone.prescription.system.prescriptionmanagementystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class PrescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Relationship to Medication entity
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    private String dosage;
    private boolean refillable;
    private String prescribingDoctor;
    private String doctorPhoneNumber;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private int refillsRemaining;
    private int unitsPerRefill;
    private boolean genericAllowed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id", nullable = false)
    private Physician physician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Custom Getters and Setters (for clarity)
    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }
}
