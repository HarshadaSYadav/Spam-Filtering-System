package com.example.Spam.Filtering.System.Repository;


import com.example.Spam.Filtering.System.Entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer>{

}
