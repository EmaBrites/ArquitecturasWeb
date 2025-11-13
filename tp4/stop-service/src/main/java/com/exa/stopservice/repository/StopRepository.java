package com.exa.stopservice.repository;

import com.exa.stopservice.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop,Integer> {

    //el 0.0001 representa una tolerancia en grados, de aproximadamente 10 metros
    @Query("SELECT s FROM Stop s WHERE ABS(s.latitude - :latitude) < 0.0001 AND ABS(s.longitude - :longitude) < 0.0001")
    Stop getStopByCoordinates(double latitude, double longitude);
}
