package com.proyect.repository;

import com.proyect.models.PrivilegeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeModel, Integer> {

    PrivilegeModel findByPrivilege(String privilege);

}
