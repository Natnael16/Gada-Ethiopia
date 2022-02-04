package com.gada.root;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface DonationRepository extends CrudRepository<Donation, Long> {
    @Query("SELECT d FROM Donation d WHERE d.post =:post ")
    List<Donation> findDonationByPostId(@Param("post") Posts post);
    
}
