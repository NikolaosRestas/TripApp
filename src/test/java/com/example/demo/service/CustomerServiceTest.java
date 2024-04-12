package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Office;
import com.example.demo.model.dto.CustomerRequestDto;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OfficeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    CustomerService customerService;
    OfficeService officeService;

    @Mock
    OfficeRepository officeRepository;
    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp(){
        officeService = new OfficeService(officeRepository);
        customerService = new CustomerService(customerRepository, officeService);
    }

    @Test
    void getAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        Office office = new Office(1L,"Kotsis","Ioannina");
        Customer customer = new Customer(1L,"Kostas","kostas@gmail.com","695521458",office);

        when(customerRepository.findAll()).thenReturn(customers);
        customers.add(customer);

        List<Customer> response = customerService.getAllCustomers();

        assertThat(response).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("Kostas");
        assertThat(response.get(0).getEmail()).isEqualTo("kostas@gmail.com");
        assertThat(response.get(0).getPhone()).isEqualTo("695521458");
    }

    @Test
    void findCustomerById(){
        Office office = new Office(1L,"Kotsis","Ioannina");
        Customer customer = new Customer(1L,"Kostas","kostas@gmail.com","695521458",office);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer response = customerService.findCustomerById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getPhone()).isEqualTo("695521458");
        assertThat(response.getName()).isEqualTo("Kostas");
        assertThat(response.getEmail()).isEqualTo("kostas@gmail.com");
    }

    @Test
    void deleteCustomerById(){
        Long requestId = 1L;
        when(customerRepository.deleteCustomerById(requestId)).thenReturn(Math.toIntExact(requestId));

        Boolean response = customerService.deleteCustomerById(requestId);

        assertThat(response).isEqualTo(true);
    }

    @Test
    void insertCustomer(){
        Office office = new Office(1L,"Kotsis","Ioannina");

        CustomerRequestDto customerRequestDto = new CustomerRequestDto("Kostas","kostas@gmail.com","695521458",office.getId());

        Customer requestCustomer = Customer.builder()
                .id(null)
                .name(customerRequestDto.getName())
                .email(customerRequestDto.getEmail())
                .phone(customerRequestDto.getPhone())
                .office(office).build();

        Customer responseCustomer = new Customer(1L,"Kostas","kostas@gmail.com","695521458",requestCustomer.getOffice());

        when(customerRepository.save(any())).thenReturn(responseCustomer);
        when(officeRepository.findById(customerRequestDto.getOfficeId())).thenReturn(Optional.of(office));

        Customer response = customerService.insertCustomer(customerRequestDto);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Kostas");
        assertThat(response.getPhone()).isEqualTo("695521458");
        assertThat(response.getEmail()).isEqualTo("kostas@gmail.com");
        assertThat(response.getOffice()).isEqualTo(office);
    }

    @Test
    void updateCustomer(){
        Office office = new Office(1L,"Kotsis","Ioannina");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("Kostas","kostas@gmail.com","695521458",office.getId());

        Customer requestCustomer = Customer.builder()
                .id(null)
                .name(customerRequestDto.getName())
                .email(customerRequestDto.getEmail())
                .phone(customerRequestDto.getPhone())
                .office(office).build();

        Customer responseCustomer = new Customer(1L,"Kostas","kostas@gmail.com","695521458",requestCustomer.getOffice());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(responseCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(responseCustomer);

        Customer response = customerService.updateCustomer(customerRequestDto,1L);

        assertThat(response.getName()).isEqualTo("Kostas");
        assertThat(response.getPhone()).isEqualTo("695521458");
        assertThat(response.getEmail()).isEqualTo("kostas@gmail.com");
    }
}
