package ru.dataart.cashMachine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dataart.cashMachine.entities.Card;
import ru.dataart.cashMachine.entities.Operation;
import ru.dataart.cashMachine.exception.NoMoneyOnBalanceException;
import ru.dataart.cashMachine.repositories.CardRepository;
import ru.dataart.cashMachine.repositories.OperationNameRepository;
import ru.dataart.cashMachine.repositories.OperationRepository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

@Service
public class MoneyWithdrawPostHandler implements OperationPostHandler {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    OperationNameRepository operationNameRepository;

    @Override
    public Integer getOperationType() {
        return 2;
    }

    @Override
    public String handle(Long cardId, Integer sum, Map<String, Object> model) {
        Card card = cardRepository.findById(cardId).get();
        Integer prevBalance = card.getCardBalance();
        //todo throw custom exception
        if (prevBalance < sum) {
            throw new NoMoneyOnBalanceException();
        }
        card.setCardBalance(prevBalance - sum);
        Operation op = new Operation();
        op.setCard(card);
        op.setPaidSum(sum);
        op.setOpName(operationNameRepository.findById(getOperationType()).get());
        op.setOpTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        operationRepository.save(op);
        cardRepository.save(card);
        model.put("cardNumber", card.getCardNumber());
        model.put("date", op.getOpTime());
        model.put("sum", sum);
        model.put("remainder", card.getCardBalance());

        return "moneyInfo";
    }
}
