package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramRequestDto {
    private String territory;
    private String price;
    private String vehicle;
    private String food;
    private List<Long> customerIds;
}
