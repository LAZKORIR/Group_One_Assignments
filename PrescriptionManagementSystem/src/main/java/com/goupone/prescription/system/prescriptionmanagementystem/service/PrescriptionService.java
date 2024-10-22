package com.goupone.prescription.system.prescriptionmanagementystem.service;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Service
public interface PrescriptionService {

    List<PrescriptionEntity> getAllPrescriptions();
    String registerUser(User user, String role, RedirectAttributes redirectAttributes);
    String home(Authentication authentication);
    String prescribeMedicine(Long patientId,List<Long> medications,String dosage);
    String dispensePrescription(Long id);
    String viewPrescriptions(Model model, Principal principal);
    String viewGenericSubstitutes(@PathVariable Long id, Model model);
    String getPhysicianHomePage(Model model);
}

