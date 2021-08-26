package ru.dataart.cashMachine.services;

import java.util.Map;

public interface OperationPostHandler extends OperationHandler{
    String handle(Long cardId,Integer sum, Map<String, Object> model);
}
