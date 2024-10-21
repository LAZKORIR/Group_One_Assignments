package com.goupone.prescription.system.prescriptionmanagementystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class PrescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dosage;
    private boolean refillable;

    private String prescribingDoctor;
    private String doctorPhoneNumber;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private int refillsRemaining;
    private int unitsPerRefill;
    private boolean isGenericAllowed;

    // Many-to-One with Patient
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Many-to-One with Physician
    @ManyToOne
    @JoinColumn(name = "physician_id", nullable = false)
    private Physician physician;


    // Relationship with Medications
    @ManyToMany
    @JoinTable(
            name = "prescription_medications",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private List<Medication> medications = new ArrayList<>();

    // Constructor
    public PrescriptionEntity() {}

    public PrescriptionEntity(String dosage, boolean refillable, String prescribingDoctor, String doctorPhoneNumber,
                              LocalDate issueDate, LocalDate expirationDate, int refillsRemaining, int unitsPerRefill,
                              boolean isGenericAllowed, Patient patient, Physician physician, List<Medication> medications) {
        this.dosage = dosage;
        this.refillable = refillable;
        this.prescribingDoctor = prescribingDoctor;
        this.doctorPhoneNumber = doctorPhoneNumber;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.refillsRemaining = refillsRemaining;
        this.unitsPerRefill = unitsPerRefill;
        this.isGenericAllowed = isGenericAllowed;
        this.patient = patient;
        this.physician = physician;
        this.medications = medications;
    }
}
