package com.geekster.Doctor.controller;

import com.geekster.Doctor.dao.DoctorRepository;
import com.geekster.Doctor.dao.PatientRepository;
import com.geekster.Doctor.model.Doctor;
import com.geekster.Doctor.model.Patient;
import com.geekster.Doctor.service.PatientService;
import jakarta.annotation.Nullable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@Component
public class PatientController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientService patientService;

    @PostMapping(value = "/patient")
    public String savePatient(@RequestBody String patient)
    {
        JSONObject json=new JSONObject(patient);
        Patient patient1=setPatient(json);
        patientService.savePatient(patient1);
        return "Patient Saved";

    }
    private Patient setPatient(JSONObject json){
        Patient patient=new Patient();
        patient.setPatientId(json.getInt("patientId"));
        patient.setPatientName(json.getString("patientName"));
        patient.setAge(json.getInt("age"));
        patient.setPhoneNumber(json.getString("phoneNumber"));
        patient.setDiseaseType(json.getString("diseaseType"));
        patient.setGender(json.getString("gender"));

        Timestamp currTime=new Timestamp(System.currentTimeMillis());
        patient.setAdmitDate(currTime);

        String doctorId=json.getString("doctorId");
        Doctor doctor=doctorRepository.findById(Integer.valueOf(doctorId)).get();
        patient.setDoctorId(doctor);

        return patient;

    }

    @GetMapping(value = "/patient")
    public List<Patient> getPatients(@Nullable @RequestParam String doctorId,
                                     @Nullable @RequestParam String patientId) {
        List<Patient> patients=new ArrayList<>();

        //both null- all patients
        //doctorId null- get by patient Id
        //patientId null- get all patients been treated by doctorId
        if(doctorId==null&&patientId==null)
        {
            return patientService.getAllPatient();
        }
        else if(doctorId==null)
        {
            patients.add(patientService.findByPatientId(Integer.valueOf(patientId)));
            return patients;
        }
        else if(patientId==null)
        {
            Integer integer = Integer.valueOf(doctorId);
            return  patientService.findPatientByDoctorId(integer);
        }
        return new ArrayList<>();




    }


}
