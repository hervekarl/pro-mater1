package com.herve.intergiciel.RHManager.RHServices;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.herve.intergiciel.RHManager.Exceptions.EmployeNotFoundException;
import com.herve.intergiciel.RHManager.Modeles.Employe;
import com.herve.intergiciel.RHManager.RHRepository.EmployesRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeService {
    
    private EmployesRepository employesRepository;

    public Employe create(Employe employe){
        return this.employesRepository.save(employe);
    }

    public List<Employe> search(){
        return this.employesRepository.findAll();
    }

    public Employe searchEmployeById(Long id){
        Employe employe=employesRepository.findById(id)
            .orElseThrow(() -> new EmployeNotFoundException("No Employe with ID "+id));
            return employe;
    }

    public Employe update( Long id, Employe employe) {

        Employe employeToUpdate=employesRepository.findById(id)
            .orElseThrow(() -> new EmployeNotFoundException("Employe not found"));

        employeToUpdate.setNom(employe.getNom());
        employeToUpdate.setPrenom(employe.getPrenom());
        employeToUpdate.setAdresse(employe.getAdresse());
        employeToUpdate.setDateEmbauche(employe.getDateEmbauche());
        employeToUpdate.setDateNaissance(employe.getDateNaissance());
        employeToUpdate.setEmail(employe.getEmail());
        employeToUpdate.setSexe(employe.getSexe());
        employeToUpdate.setTelephone(employe.getTelephone());

        return employesRepository.save(employeToUpdate);
    }

    public void delete( Long id) {

        if (!employesRepository.existsById(id)) {
            throw new EmployeNotFoundException("Not Employe with ID "+id);
        }
        employesRepository.deleteById(id);
    }

    public boolean employeExists(Long id){
        if (employesRepository.existsById(id)) {
            return true;
        } else {
            throw new EmployeNotFoundException("Not Employe with ID "+id);
            
        }
    }
    

}
