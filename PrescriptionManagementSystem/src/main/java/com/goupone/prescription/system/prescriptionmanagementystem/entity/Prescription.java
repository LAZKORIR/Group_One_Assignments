package com.goupone.prescription.system.prescriptionmanagementystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    @ToString.Exclude
    private Medication medication;

    private String dosage;
    private boolean refillable = false;
    private String prescribingDoctor;
    private String doctorPhoneNumber;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private int refillsRemaining;
    private int unitsPerRefill;
    private boolean genericAllowed;
    private boolean dispensed = false;
    private boolean partiallyDispensed=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id", nullable = false)
    @ToString.Exclude
    private Physician physician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @ToString.Exclude
    private Patient patient;

    // Constructor with required parameters
    public Prescription(Patient patient, Physician physician, Medication medication, String dosage,
                        LocalDate issueDate, LocalDate expirationDate, boolean refillable,
                        int refillsRemaining, boolean genericAllowed) {
        this.patient = patient;
        this.physician = physician;
        this.medication = medication;
        this.dosage = dosage;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.refillable = refillable;
        this.refillsRemaining = refillsRemaining;
        this.genericAllowed = genericAllowed;
    }


    public Prescription() {

    }

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
