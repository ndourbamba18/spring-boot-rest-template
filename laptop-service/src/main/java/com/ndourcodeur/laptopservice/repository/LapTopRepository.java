package com.ndourcodeur.laptopservice.repository;

import com.ndourcodeur.laptopservice.entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LapTopRepository extends JpaRepository<Laptop, Long> {

    List<Laptop> findByUserEmail(String email);
}
