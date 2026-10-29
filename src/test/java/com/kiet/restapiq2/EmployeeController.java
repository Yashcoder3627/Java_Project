package com.kiet.restapiq2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    // Temporary list to store employees in memory
    private static List<Employee> employeeList = new ArrayList<>();

    // (b) Add a new employee using @PostMapping and @RequestBody[cite: 2]
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeList.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    // (c) Retrieve all employees using @GetMapping[cite: 2]
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    // (c) Retrieve a specific employee by ID using @GetMapping[cite: 2]
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // (d) Update an employee's details using @PutMapping[cite: 2]
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        if (optionalEmployee.isPresent()) {
            Employee emp = optionalEmployee.get();
            emp.setName(updatedEmployee.getName());
            emp.setDepartment(updatedEmployee.getDepartment());
            emp.setSalary(updatedEmployee.getSalary());
            return new ResponseEntity<>(emp, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // (e) Delete an employee by ID using @DeleteMapping[cite: 2]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean removed = employeeList.removeIf(e -> e.getId().equals(id));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}