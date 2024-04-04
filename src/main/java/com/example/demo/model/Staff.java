package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "specialty")
    private String specialty;
    @Column(name = "phone")
    private String phone;
    @ManyToOne
    @JoinColumn(name = "officeId")
    private Office office;

    public Staff(){
    }


    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Staff)){
            return false;
        }
        Staff staff = (Staff) o;
        return Objects.equals(this.id,staff.id) && Objects.equals(this.name,staff.name) && Objects.equals(this.specialty,staff.specialty) && Objects.equals(this.phone,staff.phone)&& Objects.equals(this.office,staff.office);
    }
}
