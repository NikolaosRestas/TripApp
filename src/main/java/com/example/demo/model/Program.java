package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "territory")
    private String territory;
    @Column(name = "price")
    private String price;
    @Column(name = "vehicle")
    private String vehicle;
    @Column(name = "food")
    private String food;

    @ManyToMany
    @Column(name = "customer_key")
    private List<Customer> customers;

    public Program(){
    }

}
