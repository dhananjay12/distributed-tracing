package com.mynotes.spring.tracing.portalservice.crudservice.controller;

import com.mynotes.spring.tracing.portalservice.crudservice.controller.mapper.EmployeeMapper;
import com.mynotes.spring.tracing.portalservice.crudservice.dto.EmployeeDTO;
import com.mynotes.spring.tracing.portalservice.crudservice.exceptions.EntityNotFoundException;
import com.mynotes.spring.tracing.portalservice.crudservice.exceptions.ValidationException;
import com.mynotes.spring.tracing.portalservice.crudservice.persistence.EmployeeEntity;
import com.mynotes.spring.tracing.portalservice.crudservice.persistence.EmployeeRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable int id) {
        Optional<EmployeeEntity> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(EmployeeMapper.makeDTO(result.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public EmployeeDTO createCar(@Valid @RequestBody EmployeeDTO employeeDTO) {
        if(employeeDTO.getId()!=null){
            throw new ValidationException("Id should not be passed for creation");
        }
        EmployeeEntity employeeEntity = EmployeeMapper.makeEntity(employeeDTO);
        EmployeeEntity result = employeeRepository.save(employeeEntity);
        return EmployeeMapper.makeDTO(result);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateCar(
        @PathVariable int id, @Valid @RequestBody EmployeeDTO employeeDTO) throws EntityNotFoundException {
        if(employeeDTO.getId()!=null && !employeeDTO.getId().equals(id)){
            throw new ValidationException("Id in path and body does not match");
        }
        EmployeeEntity employeeEntity = findEmployeeById(id);
        employeeEntity.setFname(employeeDTO.getFname());
        employeeEntity.setLname(employeeDTO.getLname());
        employeeEntity.setEmail(employeeDTO.getEmail());
        EmployeeEntity result = employeeRepository.save(employeeEntity);
        return EmployeeMapper.makeDTO(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Optional<EmployeeEntity> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


    private EmployeeEntity findEmployeeById(Integer id) throws EntityNotFoundException {
        return employeeRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
    }

}
