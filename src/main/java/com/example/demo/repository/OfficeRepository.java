package com.example.demo.repository;

import com.example.demo.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OfficeRepository extends JpaRepository<Office,Long> {
    @Transactional
    @Modifying
    @Query("delete from Office cust where cust.id = :officeId")
    int deleteOfficeById(@Param("officeId") Long id);
}
