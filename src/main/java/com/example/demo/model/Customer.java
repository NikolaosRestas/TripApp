package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @ManyToOne
    @JoinColumn(name = "office_key")
    private Office office;

    public Customer(){

    }

    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Customer)){
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(this.id,customer.id) && Objects.equals(this.name,customer.name) && Objects.equals(this.email,customer.email) && Objects.equals(this.phone,customer.phone) && Objects.equals(this.office,customer.office);
    }

}
