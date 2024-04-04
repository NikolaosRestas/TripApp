package com.example.demo.controller;

import com.example.demo.model.Staff;
import com.example.demo.model.dto.StaffRequestDto;
import com.example.demo.service.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/staffs")
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public List<Staff> getStaffs(){
        return staffService.findAllStaff();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaff(@PathVariable("id") Long id){
        return new ResponseEntity<>(staffService.findStaffById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Staff> deleteStaff(@PathVariable("id") Long id){
        staffService.deleteStaffById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Staff> addStaff(@RequestBody StaffRequestDto staff){
        return new ResponseEntity<>(staffService.insertStaff(staff),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@RequestBody StaffRequestDto staff,@PathVariable("id") Long id){
        Staff updatedStaff = staffService.updateStaff(staff, id);
        return new ResponseEntity<>(updatedStaff,HttpStatus.OK);
    }
}
