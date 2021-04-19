package com.icai.pat.examples.dao.persistence;


import org.springframework.stereotype.Repository;
import com.icai.pat.examples.model.Role;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}