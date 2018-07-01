package com.mynotes.microservice.service2;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class EmployeeController {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(method = RequestMethod.POST, value = "/employee")
	public ResponseEntity<?> saveEmployee(@RequestBody final Employee employee) {

		employeeRepository.save(employee);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employees")
	public ResponseEntity<?> getEmployees() {
		List<Employee> employeeList = null;

		employeeList = employeeRepository.findAll();

		return ResponseEntity.ok(employeeList);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employee/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable(name = "id", required = true) long id) {
		logger.info("Get Employee By id");
		Employee employee=employeeRepository.findById(id).get();
		somethingSlow();
		return ResponseEntity.ok(employee);
	}

	private void somethingSlow() {
		logger.info("Doing some work");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

}
