package ru.dataart.cashMachine.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "operation_name")
public class OperationName {

    @Id
    @Column(name = "op_code")
    private Integer opCode;

    private String opName;
}
