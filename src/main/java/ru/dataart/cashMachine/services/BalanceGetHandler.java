package ru.dataart.cashMachine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dataart.cashMachine.entities.Card;
import ru.dataart.cashMachine.entities.Operation;
import ru.dataart.cashMachine.repositories.CardRepository;
import ru.dataart.cashMachine.repositories.OperationNameRepository;
import ru.dataart.cashMachine.repositories.OperationRepository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

@Service
public class BalanceGetHandler implements OperatorGetHandler {
    @Autowired
    OperationRepository operationRepository;

    @Autowired
    OperationNameRepository operationNameRepository;

    @Autowired
    CardRepository cardRepository;

    @Override
    public Integer getOperationType() {
        return 1;
    }

    @Override
    public String handle(Long cardId, Map<String, Object> model) {
        Operation op = new Operation();
        Optional<Card> card = cardRepository.findById(cardId);
        op.setCard(card.get());
        op.setOpName(operationNameRepository.findById(getOperationType()).get());
        op.setOpTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        operationRepository.save(op);
        model.put("cardNumber",card.get().getCardNumber());
        model.put("opTime",op.getOpTime());
        model.put("cardBalance",card.get().getCardBalance());
        return "balance";
    }
}
