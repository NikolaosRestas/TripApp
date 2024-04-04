package com.example.demo.service;

import com.example.demo.model.Office;
import com.example.demo.model.Staff;
import com.example.demo.model.dto.StaffRequestDto;
import com.example.demo.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final StaffRepository staffRepository;
    private final OfficeService officeService;

    public StaffService(StaffRepository staffRepository, OfficeService officeService) {
        this.staffRepository = staffRepository;
        this.officeService = officeService;
    }

    public List<Staff> findAllStaff(){
        return staffRepository.findAll();
    }

    public Staff findStaffById(Long id){
        return staffRepository.findById(id).orElse(null);
    }

    public boolean deleteStaffById(Long id){
        int staffDeleted = staffRepository.deleteStaffById(id);
        if(staffDeleted<=0){
            throw new RuntimeException(String.format("Cannot find Staff with id: %s",id));
        }
        return true;
    }

    public Staff insertStaff(StaffRequestDto staffRequestDto){
        final Office office = officeService.findOfficeById(staffRequestDto.getOfficeId());
        final Staff staff = Staff.builder()
                .id(null)
                .name(staffRequestDto.getName())
                .phone(staffRequestDto.getPhone())
                .specialty(staffRequestDto.getSpecialty())
                .office(office).build();
        return staffRepository.save(staff);
    }

    public Staff updateStaff(StaffRequestDto staffRequestDto,Long id){
        final Staff savedStaff = findStaffById(id);
        savedStaff.setName(staffRequestDto.getName());
        savedStaff.setSpecialty(staffRequestDto.getSpecialty());
        savedStaff.setPhone(staffRequestDto.getPhone());
        final Office office = officeService.findOfficeById(staffRequestDto.getOfficeId());
        savedStaff.setOffice(office);
        return staffRepository.save(savedStaff);
    }
}
