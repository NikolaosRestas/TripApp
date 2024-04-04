package com.example.demo.model;

import java.util.Objects;
import jakarta.persistence.*;

    @Entity
    @Table(name="office")
    // CONSTRUCTORS, GETTERS, SETTERS
    public class Office {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long id;
        @Column(name = "name")
        private String name;
        @Column(name="territory")
        private String territory;

        public Office() {
        }


        public Office(Long id,String name,String territory) {
            this.id = id;
            this.name = name;
            this.territory = territory;
        }

        public void setId(Long id){
            this.id = id;
        }

        public void setName(String name){
            this.name = name;
        }
        public void setTerritory(String territory){
            this.territory = territory;
        }

        public String getName(){
            return name;
        }

        public Long getId(){
            return id;
        }

        public String getTerritory(){
            return territory;
        }

        public boolean equals(Object o){
            if(this == o){
                return true;
            }
            if(!(o instanceof Office)){
                return false;
            }
            Office office = (Office) o;
            return Objects.equals(this.id,office.id) && Objects.equals(this.name,office.name) && Objects.equals(this.territory,office.territory);
        }


    }
