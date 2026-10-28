package com.Learn.ExceptionHandling.Controller;

import com.Learn.ExceptionHandling.Dto.EmployeeDto;
import com.Learn.ExceptionHandling.Exceptions.ResourceNotFoundException;
import com.Learn.ExceptionHandling.Service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeController (EmployeeService employeeService)
    {
        this.employeeService=employeeService;
    }

    //Controller level exception handling.
//    @ExceptionHandler
//    public ResponseEntity<String> handleEmployeeNotFound(NoSuchElementException exception)
//    {
//        return new ResponseEntity<>("Employee not found",HttpStatus.NOT_FOUND);
//    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<EmployeeDto>> GetAllEmployees()
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.GetAllEmployees());
    }

    @GetMapping("/getEmployeeById/{id}")
    public  ResponseEntity<EmployeeDto> GetEmployeeById(@PathVariable Long id)
    {
        EmployeeDto employeeDto= employeeService.GetEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.GetEmployeeById(id));
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<EmployeeDto> AddEmployee(@RequestBody @Valid EmployeeDto employeeDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.AddEmployee(employeeDto));
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public  ResponseEntity<EmployeeDto> DeleteEmployee(@PathVariable Long id)
    {
        employeeService.DeleteEmployee(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PatchMapping("/updatePartialInformationOfEmployee/{id}")
    public ResponseEntity<EmployeeDto> UpdatePartialInformationOfEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates)
    {
        return  ResponseEntity.status(HttpStatus.OK).body(employeeService.UpdatePartialInformationOfEmployee(id,updates));
    }
}
