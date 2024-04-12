package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Office;
import com.example.demo.model.Program;
import com.example.demo.model.dto.ProgramRequestDto;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OfficeRepository;
import com.example.demo.repository.ProgramRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceTest {

    @InjectMocks
    ProgramService programService;
    CustomerService customerService;
    OfficeService officeService;

    @Mock
    OfficeRepository officeRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    ProgramRepository programRepository;

    @BeforeEach
    void setUp(){
        officeService = new OfficeService(officeRepository);
        customerService = new CustomerService(customerRepository,officeService);
        programService = new ProgramService(programRepository,customerService);
    }

    @Test
    void getAllPrograms(){

        ArrayList<Program> programs = new ArrayList<Program>();
        ArrayList<Customer> customers = new ArrayList<Customer>();

        Program requestProgram = new Program(1L,"Ioannina","100$","Car","Spaghetti",customers);

        when(programRepository.findAll()).thenReturn(programs);
        programs.add(requestProgram);

        List<Program> response = programService.getAllPrograms();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1L);
        assertThat(response.get(0).getTerritory()).isEqualTo("Ioannina");
        assertThat(response.get(0).getVehicle()).isEqualTo("Car");
        assertThat(response.get(0).getPrice()).isEqualTo("100$");
        assertThat(response.get(0).getFood()).isEqualTo("Spaghetti");
    }

    @Test
    void findProgramById(){
        List<Customer> customers = new ArrayList<Customer>();
        Program responseProgram = new Program(1L,"Ioannina","100$","Car","Spaghetti",customers);

        when(programRepository.findById(1L)).thenReturn(Optional.of(responseProgram));
        Program response = programService.findProgramById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTerritory()).isEqualTo("Ioannina");
        assertThat(response.getFood()).isEqualTo("Spaghetti");
        assertThat(response.getVehicle()).isEqualTo("Car");
        assertThat(response.getPrice()).isEqualTo("100$");
    }

    @Test
    void deleteProgramById(){
        Long requestId = 1L;

        when(programRepository.deleteProgramById(requestId)).thenReturn(Math.toIntExact(requestId));

        Boolean response = programService.deleteProgramById(requestId);

        assertThat(response).isEqualTo(true);

    }

    @Test
    void insertProgram() {
        List<Long> customerIds = new ArrayList<Long>();
        List<Customer> customers = new ArrayList<Customer>();

        ProgramRequestDto programRequestDto = new ProgramRequestDto("Ioannina","100$","Car","Spaghetti",customerIds);

        Program requestProgram = Program.builder()
                .id(null)
                .food(programRequestDto.getFood())
                .price(programRequestDto.getPrice())
                .vehicle(programRequestDto.getVehicle())
                .territory(programRequestDto.getTerritory())
                .customers(customerService.findCustomersByIds(programRequestDto.getCustomerIds()))
                .build();

        Program responseProgram = new Program(1L,requestProgram.getTerritory(),requestProgram.getPrice(),requestProgram.getVehicle(),requestProgram.getFood(),requestProgram.getCustomers());

        when(customerRepository.findAllById(programRequestDto.getCustomerIds())).thenReturn(customers);
        when(programRepository.save(any())).thenReturn(responseProgram);

        Program response = programService.insertProgram(programRequestDto);


        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTerritory()).isEqualTo("Ioannina");
        assertThat(response.getVehicle()).isEqualTo("Car");
        assertThat(response.getPrice()).isEqualTo("100$");
        assertThat(response.getFood()).isEqualTo("Spaghetti");
    }

    @Test
    void updateProgram(){
        List<Long> customerIds = new ArrayList<Long>();
        List<Customer> customers = new ArrayList<Customer>();

        ProgramRequestDto programRequestDto = new ProgramRequestDto("Ioannina","100$","Car","Spaghetti",customerIds);

        Program requestProgram = Program.builder()
                .id(null)
                .food(programRequestDto.getFood())
                .price(programRequestDto.getPrice())
                .vehicle(programRequestDto.getVehicle())
                .territory(programRequestDto.getTerritory())
                .customers(customerService.findCustomersByIds(programRequestDto.getCustomerIds()))
                .build();

        Program responseProgram = new Program(1L,requestProgram.getTerritory(),requestProgram.getPrice(),requestProgram.getVehicle(),requestProgram.getFood(),requestProgram.getCustomers());

        when(programRepository.findById(1L)).thenReturn(Optional.of(responseProgram));
        when(customerRepository.findAllById(programRequestDto.getCustomerIds())).thenReturn(customers);
        when(programRepository.save(any(Program.class))).thenReturn(responseProgram);

        Program response = programService.updateProgram(programRequestDto,1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTerritory()).isEqualTo("Ioannina");
        assertThat(response.getVehicle()).isEqualTo("Car");
        assertThat(response.getPrice()).isEqualTo("100$");
        assertThat(response.getFood()).isEqualTo("Spaghetti");
    }

}
