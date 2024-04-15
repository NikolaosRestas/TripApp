package com.example.demo.controller;

import com.example.demo.model.Office;
import com.example.demo.model.Staff;
import com.example.demo.model.dto.StaffRequestDto;
import com.example.demo.service.StaffService;
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
public class StaffControllerTest {

    @InjectMocks
    StaffController staffController;
    @Mock
    StaffService staffService;

    @Test
    public void testGetStuff(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Office office = new Office(1L,"Kotsis","Igoumenitsa");

        List<Staff> staffs = new ArrayList<>();

        staffs.add(new Staff(1L,"Nikos","Guide","695525847",office));

        when(staffService.findAllStaff()).thenReturn(staffs);
        List<Staff> response = staffController.getStaffs();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getName()).isEqualTo("Nikos");
        assertThat(response.get(0).getSpecialty()).isEqualTo("Guide");
        assertThat(response.get(0).getPhone()).isEqualTo("695525847");
        assertThat(response.get(0).getId()).isEqualTo(1L);
    }

    @Test
    public void testGetStaff(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestId = 1L;
        Office office = new Office(1L,"Kotsis","Igoumenitsa");
        Staff responseStaff = new Staff(1L,"Nikos","Guide","695525847",office);

        when(staffService.findStaffById(requestId)).thenReturn(responseStaff);

        ResponseEntity<Staff> response = staffController.getStaff(requestId);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
        assertThat(response.getBody().getSpecialty()).isEqualTo("Guide");
        assertThat(response.getBody().getPhone()).isEqualTo("695525847");
    }

    @Test
    public void testAddStaff(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Office office = new Office(1L,"Kotsis","Igoumenitsa");

        StaffRequestDto requestStaff = new StaffRequestDto("Nikos","Guide","695525847",office.getId());

        Staff responseStaff = new Staff(1L,"Nikos","Guide","695525847",office);
        when(staffService.insertStaff(requestStaff)).thenReturn(responseStaff);

        ResponseEntity<Staff> response = staffController.addStaff(requestStaff);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getSpecialty()).isEqualTo("Guide");
        assertThat(response.getBody().getPhone()).isEqualTo("695525847");
    }

    @Test
    public void testDeleteStaff(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestId = 1L;
        Office office = new Office(1L,"Kotsis","Igoumenitsa");
        Staff staff = new Staff(1L,"Nikos","Guide","695525847",office);

        when(staffService.deleteStaffById(staff.getId())).thenReturn(true);

        ResponseEntity<Staff> response = staffController.deleteStaff(requestId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdateStaff(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestId = 1L;
        Office office = new Office(1L,"Kotsis","Igoumenitsa");
        Staff responseStaff = new Staff(1L,"Nikos","Guide","695525847",office);

        StaffRequestDto requestStaffDto = new StaffRequestDto("Nikos","Guide","695525847",office.getId());
        when(staffService.updateStaff(requestStaffDto,requestId)).thenReturn(responseStaff);

        ResponseEntity<Staff> response = staffController.updateStaff(requestStaffDto,requestId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getSpecialty()).isEqualTo("Guide");
        assertThat(response.getBody().getPhone()).isEqualTo("695525847");
    }

}
