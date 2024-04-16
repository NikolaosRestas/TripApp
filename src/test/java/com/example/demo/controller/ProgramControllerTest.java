package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.model.Office;
import com.example.demo.model.Program;
import com.example.demo.model.dto.ProgramRequestDto;
import com.example.demo.service.ProgramService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProgramControllerTest {
    @InjectMocks
    ProgramController programController;
    @Mock
    ProgramService programService;

    @Test
    public void testGetPrograms(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Customer> customers = new ArrayList<>();
        List<Program> programs = new ArrayList<>();

        Program program = new Program(1L,"Italy","100$","Car","Spaghetti",customers);
        programs.add(program);

        when(programService.getAllPrograms()).thenReturn(programs);
        List<Program> response = programController.getPrograms();

        assertThat(response.get(0)).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getFood()).isEqualTo("Spaghetti");
        assertThat(response.get(0).getPrice()).isEqualTo("100$");
        assertThat(response.get(0).getTerritory()).isEqualTo("Italy");
        assertThat(response.get(0).getVehicle()).isEqualTo("Car");
        assertThat(response.get(0).getCustomers()).isEqualTo(customers);
    }

    @Test
    public void testGetProgram(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestId = 1L;
        List<Customer> customers = new ArrayList<>();
        Program program = new Program(1L,"Italy","100$","Car","Spaghetti",customers);

        when(programService.findProgramById(requestId)).thenReturn(program);

        ResponseEntity<Program> response = programController.getProgram(requestId);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getFood()).isEqualTo("Spaghetti");
        assertThat(response.getBody().getPrice()).isEqualTo("100$");
        assertThat(response.getBody().getTerritory()).isEqualTo("Italy");
        assertThat(response.getBody().getVehicle()).isEqualTo("Car");
        assertThat(response.getBody().getCustomers()).isEqualTo(customers);
    }

    @Test
    public void testAddProgram(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Customer> customers = new ArrayList<>();
        List<Long> customerIds = new ArrayList<>();

        ProgramRequestDto programRequestDto = new ProgramRequestDto("Italy","100$","Car","Spaghetti",customerIds);

        Program program = new Program(1L,"Italy","100$","Car","Spaghetti",customers);

        when(programService.insertProgram(programRequestDto)).thenReturn(program);

        ResponseEntity<Program> response = programController.addProgram(programRequestDto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getFood()).isEqualTo("Spaghetti");
        assertThat(response.getBody().getPrice()).isEqualTo("100$");
        assertThat(response.getBody().getTerritory()).isEqualTo("Italy");
        assertThat(response.getBody().getVehicle()).isEqualTo("Car");
        assertThat(response.getBody().getCustomers()).isEqualTo(customers);

    }

    @Test
    public void testDeleteProgram(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Customer> customers = new ArrayList<>();

        Program program = new Program(1L,"Italy","100$","Car","Spaghetti",customers);

        when(programService.deleteProgramById(program.getId())).thenReturn(true);

        ResponseEntity<Program> response = programController.deleteProgram(program.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();

    }

    @Test
    public void testUpdateProgram(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestId = 1L;

        List<Customer> customers = new ArrayList<>();
        List<Long> customerIds = new ArrayList<>();

        ProgramRequestDto programRequestDto = new ProgramRequestDto("Italy","100$","Car","Spaghetti",customerIds);
        Program program = new Program(1L,"Italy","100$","Car","Spaghetti",customers);

        when(programService.updateProgram(programRequestDto,requestId)).thenReturn(program);
        ResponseEntity<Program> response = programController.updateProgram(programRequestDto,requestId);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getFood()).isEqualTo("Spaghetti");
        assertThat(response.getBody().getPrice()).isEqualTo("100$");
        assertThat(response.getBody().getTerritory()).isEqualTo("Italy");
        assertThat(response.getBody().getVehicle()).isEqualTo("Car");
        assertThat(response.getBody().getCustomers()).isEqualTo(customers);

    }

}
