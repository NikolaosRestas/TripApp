package com.example.demo.controller;

import com.example.demo.model.Program;
import com.example.demo.model.dto.ProgramRequestDto;
import com.example.demo.service.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/programs")
public class ProgramController {
    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }
    @GetMapping
    public List<Program> getPrograms(){
        return programService.getAllPrograms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable("id") Long id){
        return new ResponseEntity<>(programService.findProgramById(id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Program> deleteProgram(@PathVariable("id") Long id){
        programService.deleteProgramById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Program> addProgram(@RequestBody ProgramRequestDto program){
        return new ResponseEntity<>(programService.insertProgram(program),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@RequestBody ProgramRequestDto programRequestDto,@PathVariable("id") Long id){
        return new ResponseEntity<>(programService.updateProgram(programRequestDto,id),HttpStatus.OK);
    }
}
