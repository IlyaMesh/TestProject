package ru.dataart.cashMachine.services;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MoneyWithdrawGetHandler implements OperatorGetHandler {
    @Override
    public Integer getOperationType() {
        return 2;
    }

    @Override
    public String handle(Long cardId, Map<String, Object> model) {
        model.put("cardId",cardId);
        return "moneyForm";
    }
}
