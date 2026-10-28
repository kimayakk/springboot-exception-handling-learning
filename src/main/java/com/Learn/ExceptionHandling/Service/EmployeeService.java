package com.Learn.ExceptionHandling.Service;

import com.Learn.ExceptionHandling.Dto.EmployeeDto;
import com.Learn.ExceptionHandling.Entity.EmployeeEntity;
import com.Learn.ExceptionHandling.Exceptions.ResourceNotFoundException;
import com.Learn.ExceptionHandling.Repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private  ObjectMapper objectMapper;

    public  EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, ObjectMapper objectMapper)
    {
     this.employeeRepository=employeeRepository;
     this.modelMapper=modelMapper;
     this.objectMapper=objectMapper;

    }

    public List<EmployeeDto> GetAllEmployees() {
        List<EmployeeEntity> employee= employeeRepository.findAll();
        return employee.stream().map(e->modelMapper.map(e,EmployeeDto.class)).toList();
    }

    public EmployeeDto GetEmployeeById(Long id)
    {
        EmployeeEntity employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found in service."));
        return  modelMapper.map(employee,EmployeeDto.class);
    }

    public  EmployeeDto AddEmployee(EmployeeDto employeeDto)
    {
         EmployeeEntity employee = modelMapper.map(employeeDto,EmployeeEntity.class);
         employee=employeeRepository.save(employee);
         return modelMapper.map(employee,EmployeeDto.class);
    }

    public void DeleteEmployee(Long id)
    {
        employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found to delete the record"));
        employeeRepository.deleteById(id);
    }

    public EmployeeDto UpdatePartialInformationOfEmployee(Long id, Map<String,Object> updates)
    {
        EmployeeEntity employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not present in DB to update"));

        objectMapper.updateValue(employee,updates);

        return modelMapper.map(employeeRepository.save(employee),EmployeeDto.class);
    }
}
