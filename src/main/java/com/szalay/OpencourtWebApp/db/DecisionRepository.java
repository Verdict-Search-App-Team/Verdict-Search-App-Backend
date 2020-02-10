package com.szalay.OpencourtWebApp.db;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface DecisionRepository extends CrudRepository<DecisionDto, String> {

    List<DecisionDto> findByBirosagneveContaining(String query);
}
