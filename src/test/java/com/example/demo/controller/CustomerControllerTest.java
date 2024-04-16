package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.model.Office;
import com.example.demo.model.dto.CustomerRequestDto;
import com.example.demo.service.CustomerService;
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
public class CustomerControllerTest {
    @InjectMocks
    CustomerController customerController;
    @Mock
    CustomerService customerService;

    @Test
    public void testGetCustomers(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Office office = new Office(1L,"Kotsis","Igoumenitsa");

        List<Customer> customers = new ArrayList<>();

        Customer customer = new Customer(1L,"Nikos","nikos@gmail.com","695521547",office);
        customers.add(customer);

        when(customerService.getAllCustomers()).thenReturn(customers);

        List<Customer> response = customerController.getCustomers();

        assertThat(response.get(0)).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("Nikos");
        assertThat(response.get(0).getEmail()).isEqualTo("nikos@gmail.com");
        assertThat(response.get(0).getPhone()).isEqualTo("695521547");
        assertThat(response.get(0).getOffice()).isEqualTo(office);
    }

    @Test
    public void testGetCustomer(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestId = 1L;
        Office office = new Office(1L,"Kotsis","Igoumenitsa");
        Customer customer = new Customer(1L,"Nikos","nikos@gmail.com","695521547",office);

        when(customerService.findCustomerById(requestId)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomer(requestId);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
        assertThat(response.getBody().getEmail()).isEqualTo("nikos@gmail.com");
        assertThat(response.getBody().getPhone()).isEqualTo("695521547");
        assertThat(response.getBody().getOffice()).isEqualTo(office);
    }

    @Test
    public void testAddCustomer(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Office office = new Office(1L,"Kotsis","Igoumenitsa");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("Nikos","nikos@gmail.com","695521547",office.getId());

        Customer customerResponse = new Customer(1L,"Nikos","nikos@gmail.com","695521547",office);

        when(customerService.insertCustomer(customerRequestDto)).thenReturn(customerResponse);

        ResponseEntity<Customer> response = customerController.addCustomer(customerRequestDto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
        assertThat(response.getBody().getEmail()).isEqualTo("nikos@gmail.com");
        assertThat(response.getBody().getPhone()).isEqualTo("695521547");
        assertThat(response.getBody().getOffice()).isEqualTo(office);
    }

    @Test
    public void testDeleteCustomer(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Office office = new Office(1L,"Kotsis","Igoumenitsa");
        Customer customer = new Customer(1L,"Nikos","nikos@gmail.com","695521547",office);

        when(customerService.deleteCustomerById(customer.getId())).thenReturn(true);

        ResponseEntity<Customer> response = customerController.deleteCustomer(customer.getId());

        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testUpdateCustomer(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestId = 1L;
        Office office = new Office(1L,"Kotsis","Igoumenitsa");
        Customer customer = new Customer(1L,"Restas","nikos@gmail.com","695521547",office);

        CustomerRequestDto customerRequestDto = new CustomerRequestDto("Nikos","nikos@gmail.com","695521547", office.getId());

        when(customerService.updateCustomer(customerRequestDto,requestId)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.updateCustomer(customerRequestDto,requestId);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Restas");
        assertThat(response.getBody().getEmail()).isEqualTo("nikos@gmail.com");
        assertThat(response.getBody().getPhone()).isEqualTo("695521547");
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getOffice()).isEqualTo(office);
    }

}
