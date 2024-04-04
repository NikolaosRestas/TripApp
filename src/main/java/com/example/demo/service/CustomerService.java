package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Office;
import com.example.demo.model.dto.CustomerRequestDto;
import com.example.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OfficeService officeService;

    public CustomerService(CustomerRepository customerRepository, OfficeService officeService) {
        this.customerRepository = customerRepository;
        this.officeService = officeService;
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public boolean deleteCustomerById(Long id){
        int deletedCustomer = customerRepository.deleteCustomerById(id);
        if(deletedCustomer<=0){
            throw new RuntimeException(String.format("Cannot find Customer with id: %s",id));
        }
        return true;
    }
    public List<Customer> findCustomersByIds(List<Long> customerIds) {
        return customerRepository.findAllById(customerIds);
    }

    public Customer insertCustomer(CustomerRequestDto customerRequestDto){
        final Office office = officeService.findOfficeById(customerRequestDto.getOfficeId());
        final Customer customer = Customer.builder()
                .id(null)
                .name(customerRequestDto.getName())
                .email(customerRequestDto.getEmail())
                .phone(customerRequestDto.getPhone())
                .office(office).build();
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(CustomerRequestDto customerRequestDto,Long id){
        final Customer savedCustomer = findCustomerById(id);
        savedCustomer.setName(customerRequestDto.getName());
        savedCustomer.setEmail(customerRequestDto.getEmail());
        savedCustomer.setPhone(customerRequestDto.getPhone());
        final Office office = officeService.findOfficeById(customerRequestDto.getOfficeId());
        savedCustomer.setOffice(office);
        return customerRepository.save(savedCustomer);
    }
}
