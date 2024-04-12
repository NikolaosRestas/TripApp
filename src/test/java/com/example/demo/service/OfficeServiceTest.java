package com.example.demo.service;

import com.example.demo.model.Office;
import com.example.demo.repository.OfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class OfficeServiceTest{

    @InjectMocks
    OfficeService officeService;

    @Mock
    OfficeRepository officeRepository;


    @Test
    void getAllOffices(){
        Office office = new Office(1L,"Kotsis","Ioannina");
        List<Office> offices = new ArrayList<Office>();

        when (officeRepository.findAll()).thenReturn(offices);

        offices.add(office);
        List<Office> response = officeService.getAllOffices();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getTerritory()).isEqualTo("Ioannina");
        assertThat(response.get(0).getName()).isEqualTo("Kotsis");

    }

    @Test
    void findOfficeById(){
        Office responseOffice = new Office(1L,"Kotsis","Ioannina");

        when(officeRepository.findById(1L)).thenReturn(Optional.of(responseOffice));

        Office response = officeService.findOfficeById(1L);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Kotsis");
        assertThat(response.getTerritory()).isEqualTo("Ioannina");

    }

    @Test
    void deleteOfficeById(){
        Long requestOffice = 1L;

        when(officeRepository.deleteOfficeById(requestOffice)).thenReturn(Math.toIntExact(requestOffice));

        Boolean response = officeService.deleteOfficeById(requestOffice);
        assertThat(response).isEqualTo(true);
    }

    @Test
    void insertOffice(){
        Office requestOffice = new Office(1L,"Kotsis","Ioannina");
        Office responseOffice = new Office(1L,"Kotsis","Ioannina");

        when(officeRepository.save(requestOffice)).thenReturn(responseOffice);

        Office response = officeService.insertOffice(requestOffice);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Kotsis");
        assertThat(response.getTerritory()).isEqualTo("Ioannina");
    }

    @Test
    void updateOffice(){
        Office requestOffice = new Office(1L,"Kotsis","Ioannina");
        Office responseOffice = new Office(1L,"Nikos","Igoumenitsa");

        when(officeRepository.save(requestOffice)).thenReturn(responseOffice);


        List<Office> offices = new ArrayList<Office>();
        offices.add(requestOffice);

        Office response = officeService.insertOffice(requestOffice);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Nikos");
        assertThat(response.getTerritory()).isEqualTo("Igoumenitsa");


    }
}
