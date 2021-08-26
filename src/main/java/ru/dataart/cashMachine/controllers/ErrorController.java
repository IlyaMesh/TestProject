package ru.dataart.cashMachine.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.dataart.cashMachine.entities.Error;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Controller
public class ErrorController {

    @RequestMapping(value = "/error/{errorCode}", method = RequestMethod.GET)
    public String showErrorPage(@PathVariable("errorCode") Integer errorCode, Map<String, Object> model) {
        Error[] values = Error.values();
        Error error = Arrays.stream(values).filter(value -> value.getCode().equals(errorCode)).findFirst().get();
        log.info("Error page gonna be shown");
        model.put("errorText", error.getDescription());
        return "error";
    }
}
