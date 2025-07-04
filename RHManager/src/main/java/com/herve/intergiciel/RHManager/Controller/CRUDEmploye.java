package com.herve.intergiciel.RHManager.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.herve.intergiciel.RHManager.Modeles.Employe;
import com.herve.intergiciel.RHManager.RHRepository.EmployesRepository;
import com.herve.intergiciel.RHManager.RHServices.EmployeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rh/employe")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CRUDEmploye {

    private final EmployeService employeService;
   
    // @PostMapping(path = "employes/create", consumes = "application/json")
    // public Employe create(@RequestBody Employe employe) {
    //     return employesRepository.save(employe);
    // }
    
    // @GetMapping(path = "/employes", produces  = "application/json")
    // public List<Employe> getAllEmployes() {
    //     return employesRepository.findAll();
    // }

    @PostMapping(path = "/create", consumes = "application/json")
    public ResponseEntity<Employe> create(@RequestBody Employe infoPatient) {
        return ResponseEntity.ok(employeService.create(infoPatient));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Employe>> search() {
        List<Employe> employes=employeService.search();
        return ResponseEntity.ok(employes);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.searchEmployeById(id));
    }

    @PutMapping(path = "update/{id}", consumes = "application/json")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Long id, @RequestBody Employe employe) {
        return ResponseEntity.ok(employeService.update(id , employe));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmploye(@PathVariable Long id) {
        employeService.delete(id);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> employeExists(@PathVariable Long id) {
        boolean exists = employeService.employeExists(id);
        return ResponseEntity.ok(exists);
    }
}
