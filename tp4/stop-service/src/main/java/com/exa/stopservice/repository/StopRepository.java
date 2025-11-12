package com.exa.stopservice.repository;

import com.exa.stopservice.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop,Integer> {

    @Query("SELECT s FROM Stop s WHERE s.latitude= :latitude AND s.longitude= :longitude")
    Stop getStopByCoordinates(double latitude, double longitude);
}
