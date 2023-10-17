package com.digitalinnovationone.gof.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author JVPCoder
 */

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
