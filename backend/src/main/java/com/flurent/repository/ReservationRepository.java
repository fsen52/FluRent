package com.flurent.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flurent.domain.Car;
import com.flurent.domain.Reservation;
import com.flurent.domain.User;
import com.flurent.domain.enums.ReservationStatus;
import com.flurent.dto.ReservationDTO;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	boolean existsByCar(Car car);
	
	boolean existsByUser(User user);
	
	List<ReservationDTO> findAllBy();
	
	List<ReservationDTO> findAllByUserId(Long userId);
	
	Optional<ReservationDTO> findReservationDTOById(Long id);
	
	Optional<ReservationDTO> findByIdAndUser(Long reservationId, User user);

	@Query("SELECT r FROM Reservation r "
			+ "JOIN FETCH Car c on r.car=c WHERE "
			+ "c.id=:carId and (r.status not in :status) and :pickUpTime BETWEEN r.pickUpTime and r.dropOffTime "
			+ "or "
			+ "c.id=:carId and (r.status not in :status) and :dropOffTime BETWEEN r.pickUpTime and r.dropOffTime "
			+ "or "
			+ "c.id=:carId and (r.status not in :status) and (r.pickUpTime BETWEEN :pickUpTime and :dropOffTime)")
	List<Reservation> checkCarStatus(@Param("carId") Long carId, @Param("pickUpTime") LocalDateTime pickUpTime,
			@Param("dropOffTime") LocalDateTime dropOffTime, @Param("status") ReservationStatus [] status);

	

}
