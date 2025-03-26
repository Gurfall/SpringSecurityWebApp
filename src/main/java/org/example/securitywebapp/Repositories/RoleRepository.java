package org.example.securitywebapp.Repositories;


import org.example.securitywebapp.Model.ERole;
import org.example.securitywebapp.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(ERole name);
}
