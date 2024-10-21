package com.goupone.prescription.system.prescriptionmanagementystem.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Prescription {
    private int prescriptionID;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String patient;
    private String prescribingPhysician;
    private String pharmacist;

    // Constructor
    public Prescription(int prescriptionID, String issueDate, String expirationDate,
                        String patient, String prescribingPhysician, String pharmacist) {
        this.prescriptionID = prescriptionID;
        this.issueDate = LocalDate.parse(issueDate);
        this.expirationDate = LocalDate.parse(expirationDate);
        this.patient = patient;
        this.prescribingPhysician = prescribingPhysician;
        this.pharmacist = pharmacist;
    }

    // toString() method for easy display
    @Override
    public String toString() {
        return "PrescriptionEntity{" +
                "prescriptionID=" + prescriptionID +
                ", issueDate=" + issueDate +
                ", expirationDate=" + expirationDate +
                ", patient='" + patient + '\'' +
                ", prescribingPhysician='" + prescribingPhysician + '\'' +
                ", pharmacist='" + pharmacist + '\'' +
                '}';
    }

}
