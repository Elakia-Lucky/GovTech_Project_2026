package com.govtech.repository;

import com.govtech.entity.RestaurantOption;
import com.govtech.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantOptionRepository extends JpaRepository<RestaurantOption, Long> {

    List<RestaurantOption> findBySession(Session session);
}
