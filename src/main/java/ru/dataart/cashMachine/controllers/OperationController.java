package ru.dataart.cashMachine.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dataart.cashMachine.entities.Error;
import ru.dataart.cashMachine.entities.OperationName;
import ru.dataart.cashMachine.exception.NoMoneyOnBalanceException;
import ru.dataart.cashMachine.repositories.CardRepository;
import ru.dataart.cashMachine.repositories.OperationNameRepository;
import ru.dataart.cashMachine.repositories.OperationRepository;
import ru.dataart.cashMachine.services.OperationPostHandler;
import ru.dataart.cashMachine.services.OperatorGetHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
public class OperationController {

    @Autowired
    private List<OperatorGetHandler> getHandlers;

    @Autowired
    private List<OperationPostHandler> postHandlers;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    OperationNameRepository operationNameRepository;

    @RequestMapping(value = "/card/{card}/operations", method = RequestMethod.GET)
    public String showPinForm(@PathVariable("card") Long cardId, Map<String, Object> model) {
        Iterable<OperationName> all = operationNameRepository.findAll();
        model.put("operations", StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList()));
        model.put("cardId", cardId);
        return "operations";
    }

    @RequestMapping(value = "/card/{card}/operation/{opCode}", method = RequestMethod.GET)
    public String getOperation(@PathVariable("card") Long cardId, @PathVariable("opCode") Integer opCode, Map<String, Object> model) {
        log.info("Got card with id: " + cardId);
        log.info("Got operation with opCode: " + opCode);
        Optional<String> res = getHandlers.stream().map(operatorGetHandler -> {
            if (operatorGetHandler.getOperationType().equals(opCode))
                return operatorGetHandler.handle(cardId, model);
            return null;
        }).filter(Objects::nonNull).findFirst();
        if (res.isEmpty()) {
            return "redirect:/error/+" + Error.OPERATION_NOT_SUPPORTED.getCode();
        } else return res.get();
    }

    @RequestMapping(value = "/card/{card}/operation/{opCode}", method = RequestMethod.POST)
    public String postOperation(@PathVariable("card") Long cardId, @PathVariable("opCode") Integer opCode, @RequestParam("sum") Integer sum, Map<String, Object> model) {

        try {
            Optional<String> res = postHandlers.stream().map(operatorPostHandler -> {
                if (operatorPostHandler.getOperationType().equals(opCode))
                    return operatorPostHandler.handle(cardId, sum, model);
                return null;
            }).filter(Objects::nonNull).findFirst();
            if (res.isEmpty())
                return "redirect:/error/+" + Error.OPERATION_NOT_SUPPORTED.getCode();
            return res.get();
        } catch (NoMoneyOnBalanceException ex) {
            log.warn("Not enough money for operation");
            return "redirect:/error/+" + Error.NOT_ENOUGH_MONEY.getCode();
        }

    }
}
