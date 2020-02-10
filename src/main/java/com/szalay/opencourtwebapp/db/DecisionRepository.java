package com.szalay.opencourtwebapp.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DecisionRepository extends CrudRepository<DecisionDto, String> {

    List<DecisionDto> findByBirosagneveContaining(String query);
}
