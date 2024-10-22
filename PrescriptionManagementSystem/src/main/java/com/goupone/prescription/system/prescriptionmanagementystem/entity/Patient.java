package com.goupone.prescription.system.prescriptionmanagementystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String insuranceProvider;
    private String insurancePolicyNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PrescriptionEntity> prescriptionEntities = new ArrayList<>();

}


