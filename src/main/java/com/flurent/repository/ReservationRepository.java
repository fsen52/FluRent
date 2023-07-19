package com.flurent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flurent.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
