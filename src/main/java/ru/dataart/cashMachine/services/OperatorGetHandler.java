package ru.dataart.cashMachine.services;

import java.util.Map;

public interface OperatorGetHandler extends OperationHandler {

    String handle(Long cardId, Map<String, Object> model);
}
