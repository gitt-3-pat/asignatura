package com.icai.pat.examples.dao.persistence;

import org.springframework.stereotype.Repository;
import com.icai.pat.examples.model.Privilege;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}