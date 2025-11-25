package com.exa.tripservice.repository;

import com.exa.tripservice.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByAccountId(Long accountId);
    List<Trip> findByScooterId(Long scooterId);

    @Query("SELECT t FROM Trip t WHERE " +
            "(:accountId IS NULL OR t.accountId = :accountId) AND " +
            "(:scooterId IS NULL OR t.scooterId = :scooterId) AND " +
            "(:from IS NULL OR t.startTime >= :from) AND " +
            "(:to IS NULL OR t.endTime <= :to)")
    List<Trip> findFilteredTrips(
            @Param("accountId") Long accountId,
            @Param("scooterId") Long scooterId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

        @Query("SELECT t.scooterId FROM Trip t WHERE FUNCTION('YEAR', t.startTime) = :year GROUP BY t.scooterId HAVING COUNT(t.id) >= :minTrips")
        List<Long> findScootersWithMinTripsByYear(@Param("year") int year, @Param("minTrips") int minTrips);
}
