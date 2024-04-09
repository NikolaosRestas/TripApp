package com.example.demo.service;

import com.example.demo.model.Office;
import com.example.demo.model.Staff;
import com.example.demo.model.dto.StaffRequestDto;
import com.example.demo.repository.OfficeRepository;
import com.example.demo.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {

    @InjectMocks
    StaffService staffService;
    OfficeService officeService;

    @Mock
    OfficeRepository officeRepository;

    @Mock
    StaffRepository staffRepository;

    @BeforeEach
    void setUp(){
        officeService = new OfficeService(officeRepository);
        staffService = new StaffService(staffRepository,officeService);
    }


    @Test
    void getAllStaff(){
        List<Staff> stuffs = new ArrayList<Staff>();
        Office office = new Office( 1L,"Kotsis","Ioannina");
        Staff staff = new Staff(1L,"Nikos","Guide","695582145",office);

        when(staffRepository.findAll()).thenReturn(stuffs);

        stuffs.add(staff);

        List<Staff> response = staffService.findAllStaff();

        assertThat(response).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("Nikos");
        assertThat(response.get(0).getPhone()).isEqualTo("695582145");
        assertThat(response.get(0).getSpecialty()).isEqualTo("Guide");
        assertThat(response.get(0).getOffice()).isEqualTo(office);
    }

    @Test
    void findStaffById(){
        Office office = new Office( 1L,"Kotsis","Ioannina");
        Staff responseStaff = new Staff(1L,"Nikos","Guide","695582145",office);

        when(staffRepository.findById(1L)).thenReturn(Optional.of(responseStaff));

        Staff response = staffService.findStaffById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Nikos");
        assertThat(response.getSpecialty()).isEqualTo("Guide");
        assertThat(response.getPhone()).isEqualTo("695582145");
        assertThat(response.getOffice()).isEqualTo(office);
    }

    @Test
    void deleteStaffById(){
        Long requestId = 1L;

        when(staffRepository.deleteStaffById(requestId)).thenReturn(Math.toIntExact(requestId));

        Boolean response = staffService.deleteStaffById(requestId);
        assertThat(response).isEqualTo(true);
    }

    @Test
    void insertStaff(){
        Office office = new Office( 1L,"Kotsis","Ioannina");

        StaffRequestDto requestStaffDto = new StaffRequestDto("Kotsis","Guide","695525148",office.getId());

        Staff requestStaff = Staff.builder()
                .id(null)
                .name(requestStaffDto.getName())
                .specialty(requestStaffDto.getSpecialty())
                .phone(requestStaffDto.getPhone())
                .office(office).build();

        Staff responseStaff = new Staff(1L,"Kotsis","Guide","695525148",requestStaff.getOffice());

        when(staffRepository.save(any())).thenReturn(responseStaff);
        when(officeRepository.findById(requestStaffDto.getOfficeId())).thenReturn(Optional.of(office));

        Staff response = staffService.insertStaff(requestStaffDto);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Kotsis");
        assertThat(response.getSpecialty()).isEqualTo("Guide");
        assertThat(response.getPhone()).isEqualTo("695525148");
        assertThat(response.getOffice()).isEqualTo(office);

    }

    @Test
    void updateStaff(){
        Office office = new Office(1L,"Kotsis","Ioannina");

        StaffRequestDto requestStaffDto = new StaffRequestDto("Kotsis","Guide","695525148",office.getId());

        Staff requestStaff = Staff.builder()
                .id(null)
                .name(requestStaffDto.getName())
                .specialty(requestStaffDto.getSpecialty())
                .phone(requestStaffDto.getPhone())
                .office(office).build();

        Staff responseStaff = new Staff(1L,"Kotsis","Guide","695525148",requestStaff.getOffice());

        when(staffRepository.findById(1L)).thenReturn(Optional.of(responseStaff));
        when(staffRepository.save(any(Staff.class))).thenReturn(responseStaff);

        Staff response = staffService.updateStaff(requestStaffDto,1L);

        assertThat(response.getName()).isEqualTo("Kotsis");
        assertThat(response.getSpecialty()).isEqualTo("Guide");
        assertThat(response.getPhone()).isEqualTo("695525148");
    }

}
