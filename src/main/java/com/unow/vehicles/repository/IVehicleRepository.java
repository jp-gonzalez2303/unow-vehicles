package com.unow.vehicles.repository;

import com.unow.vehicles.entities.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IVehicleRepository extends JpaRepository<VehicleEntity, Integer> {


    @Query("SELECT v FROM VehicleEntity v WHERE LOWER(v.make)  LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR  LOWER(v.plate)  LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR  LOWER(v.model)  LIKE LOWER(CONCAT('%', :filter, '%')) ")
    Page<VehicleEntity> findAllPagedByFilter(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT v FROM VehicleEntity v WHERE LOWER(v.plate) =LOWER(:plate)")
    Optional<VehicleEntity> findByPlate(@Param("plate") String plate);


    @Query("SELECT v FROM VehicleEntity v WHERE LOWER(v.plate) =:plate")
    List<VehicleEntity> findByPlateList(@Param("plate") String plate);

    @Query("SELECT v FROM VehicleEntity v WHERE LOWER(v.make) =:make")
    List<VehicleEntity> findByMake(@Param("make") String make);

    @Query("SELECT v FROM VehicleEntity v WHERE LOWER(v.model) =:model")
    List<VehicleEntity> findByModel(@Param("model") String model);
}
