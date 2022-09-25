package com.proyect.repository;

import com.proyect.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer> {

    RoleModel findByRole(String rol);
}
