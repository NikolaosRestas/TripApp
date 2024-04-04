package com.example.demo.service;

import com.example.demo.model.Office;
import com.example.demo.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {
    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public List<Office> getAllOffices(){
        return this.officeRepository.findAll();
    }

    public Office findOfficeById(Long id){
        return officeRepository.findById(id).orElse(null);
    }

    public boolean deleteOfficeById(Long id){
        final int deletedCount = officeRepository.deleteOfficeById(id);

        if(deletedCount<=0){
            throw new RuntimeException(String.format("Cannot find Office with id: %s",id));
        }
        return true;
    }

    public Office insertOffice(Office office){
        office.setId(null);
        return officeRepository.save(office);
    }

    public Office updateOffice(Office office){
        Office savedOffice = findOfficeById(office.getId());
        savedOffice.setName(office.getName());
        savedOffice.setTerritory(office.getTerritory());
        return officeRepository.save(savedOffice);
    }

}
