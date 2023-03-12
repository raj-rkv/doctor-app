package com.geekster.Doctor.service;

import com.geekster.Doctor.dao.PatientRepository;
import com.geekster.Doctor.model.Doctor;
import com.geekster.Doctor.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    public void savePatient(Patient patient)
    {
        patientRepository.save(patient);
    }

    public List<Patient> getAllPatient()
    {
        return patientRepository.findAll();
    }

    public Patient findByPatientId(Integer id){
        return patientRepository.findById(id).get();
    }

    public List<Patient> findPatientByDoctorId(Integer doctorId){
        List<Patient> tempPatient=patientRepository.findAll();
        List<Patient> ans=new ArrayList<>();
        for(Patient patient:tempPatient)
        {
            if(patient.getDoctorId().getDoctorId()==doctorId){
                ans.add(patient);
            }
        }
        return ans;


    }
}
