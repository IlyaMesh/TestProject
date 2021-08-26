package ru.dataart.cashMachine.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.dataart.cashMachine.entities.OperationName;

public interface OperationNameRepository extends CrudRepository<OperationName,Integer> {
}
