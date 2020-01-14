package com.example.demo.repository;

import com.example.demo.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

//    @Query(nativeQuery = true,value = "SELECT * FROM PERSON WHERE EMAIL=")
    Person findByEmail(String email);
}
