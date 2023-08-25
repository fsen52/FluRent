package com.flurent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flurent.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
	
	
	//Wenn wir custom query benutzen, müssen wir "@Modifying" annotation schreiben
	@Modifying
	@Query("UPDATE User u SET u.firstName=:firstName,u.lastName=:lastName,u.email=:email,u.phoneNumber=:phoneNumber,u.address=:address,"
			+ "u.zipCode=:zipCode WHERE u.id=:id")
	void update(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName, 
				@Param("email") String email, @Param("phoneNumber") String phoneNumber,@Param("address") String address,
				@Param("zipCode") String zipCode);
	
	

}
