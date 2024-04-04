package com.example.demo.controller;

import com.example.demo.model.Office;
import com.example.demo.service.OfficeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/offices")
public class OfficeController {
    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public ResponseEntity<List<Office>> getOffices() {
        return new ResponseEntity<>(officeService.getAllOffices(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Office> getOffice(@PathVariable("id") Long id) {
        return new ResponseEntity<>(officeService.findOfficeById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Office> addOffice(@RequestBody Office office) {
        final Office createdOffice = officeService.insertOffice(office);
        return new ResponseEntity<>(createdOffice, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOffice(@PathVariable("id") long id) {
        officeService.deleteOfficeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Office> updateOffice(@RequestBody Office office) {
        Office updatedOffice = officeService.updateOffice(office);
        return new ResponseEntity<>(updatedOffice, HttpStatus.OK);
    }
}
