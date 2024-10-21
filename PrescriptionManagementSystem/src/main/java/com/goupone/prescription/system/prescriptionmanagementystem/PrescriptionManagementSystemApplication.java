package com.goupone.prescription.system.prescriptionmanagementystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.goupone.prescription.system")
public class PrescriptionManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrescriptionManagementSystemApplication.class, args);
	}

}
