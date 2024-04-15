package com.example.demo.controller;

import com.example.demo.model.Office;
import com.example.demo.service.OfficeService;
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
public class OfficeControllerTest {
    @InjectMocks
    OfficeController officeController;
    @Mock
    OfficeService officeService;

    @Test
    public void testGetOffices(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Office> offices = new ArrayList<>();
        offices.add(new Office(1L,"Kotsis","Igoumenitsa"));

        when(officeService.getAllOffices()).thenReturn(offices);
        ResponseEntity<List<Office>> response = officeController.getOffices();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Kotsis");
        assertThat(response.getBody().get(0).getTerritory()).isEqualTo("Igoumenitsa");
        assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
    }

    @Test
    public void testGetOffice(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestId = 1L;
        Office responseOffice = new Office(requestId,"Kotsis","Igoumenitsa");

        when(officeService.findOfficeById(requestId)).thenReturn(responseOffice);

        ResponseEntity<Office> response = officeController.getOffice(requestId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Kotsis");
        assertThat(response.getBody().getTerritory()).isEqualTo("Igoumenitsa");
        assertThat(response.getBody().getId()).isEqualTo(requestId);
    }

    @Test
    public void testAddOffice(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Office requestOffice = new Office(null,"Kotsis","Igoumenitsa");
        Office responseOffice = new Office(1L,"Kotsis","Igoumenitsa");

        when(officeService.insertOffice(requestOffice)).thenReturn(responseOffice);

        ResponseEntity<Office> response = officeController.addOffice(requestOffice);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Kotsis");
        assertThat(response.getBody().getTerritory()).isEqualTo("Igoumenitsa");

    }

    @Test
    public void testDeleteOffice(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Office requestOffice = new Office(1L,"Kotsis","Igoumenitsa");
        when(officeService.deleteOfficeById(requestOffice.getId())).thenReturn(true);

        ResponseEntity<Boolean> response = officeController.deleteOffice(requestOffice.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdateOffice(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Office requestOffice = new Office(1L,"Kotsis","Ioannina");
        Office responseOffice = new Office(1L,"Kotsis","Igoumenitsa");

        when(officeService.updateOffice(requestOffice)).thenReturn(responseOffice);

        ResponseEntity<Office> response = officeController.updateOffice(requestOffice);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Kotsis");
        assertThat(response.getBody().getTerritory()).isEqualTo("Igoumenitsa");
    }
}
