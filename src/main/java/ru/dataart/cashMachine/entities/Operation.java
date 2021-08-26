package ru.dataart.cashMachine.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "operation")
public class Operation {

    @Id
    @Column(name = "operation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;

    private Timestamp opTime;
    @Column(name = "paid_sum")
    private Integer paidSum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "op_code", nullable = false)
    private OperationName opName;
}
