package ru.dataart.cashMachine.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.dataart.cashMachine.entities.Operation;

public interface OperationRepository extends CrudRepository<Operation, Long> {


}
