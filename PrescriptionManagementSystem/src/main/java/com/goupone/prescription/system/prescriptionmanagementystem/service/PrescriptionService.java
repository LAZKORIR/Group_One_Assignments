package com.goupone.prescription.system.prescriptionmanagementystem.service;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public interface PrescriptionService {

    List<PrescriptionEntity> getAllPrescriptions();
    String registerUser(User user, String role, RedirectAttributes redirectAttributes);
    String home(Authentication authentication);
    String prescribeMedicine(Long patientId,List<Long> medications,String dosage);
}

