package com.example.demo.service;

import com.example.demo.model.Program;
import com.example.demo.model.dto.ProgramRequestDto;
import com.example.demo.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {
    private final ProgramRepository programRepository;
    private final CustomerService customerService;

    public ProgramService(ProgramRepository programRepository, CustomerService customerService) {
        this.programRepository = programRepository;
        this.customerService = customerService;
    }

    public List<Program> getAllPrograms(){
        return programRepository.findAll();
    }

    public Program findProgramById(Long id){
        return programRepository.findById(id).orElse(null);
    }

    public boolean deleteProgramById(Long id){
        final int deletedCount = programRepository.deleteProgramById(id);
        if(deletedCount<=0){
            throw new RuntimeException(String.format("Cannot find program with id: %s",id));
        }
        return true;
    }

    public Program insertProgram(ProgramRequestDto programRequestDto){
        final Program program  = Program.builder()
                .id(null)
                .territory(programRequestDto.getTerritory())
                .food(programRequestDto.getFood())
                .price(programRequestDto.getPrice())
                .vehicle(programRequestDto.getVehicle())
                .customers(customerService.findCustomersByIds(programRequestDto.getCustomerIds()))
                .build();
        return programRepository.save(program);
    }

    public Program updateProgram(ProgramRequestDto programRequestDto,long id){
        Program savedProgram = findProgramById(id);
        savedProgram.setTerritory(programRequestDto.getTerritory());
        savedProgram.setFood(programRequestDto.getFood());
        savedProgram.setPrice(programRequestDto.getPrice());
        savedProgram.setVehicle(programRequestDto.getVehicle());
        savedProgram.setCustomers(customerService.findCustomersByIds(programRequestDto.getCustomerIds()));
        return programRepository.save(savedProgram);

    }
}
