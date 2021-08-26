package ru.dataart.cashMachine.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.dataart.cashMachine.entities.Card;
import ru.dataart.cashMachine.entities.Error;
import ru.dataart.cashMachine.repositories.CardRepository;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    private final Integer maxRetries = 4;
    private Integer unsuccessfulTries = 0;

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String handlePass(@RequestParam("cardNumber") String cardNumber, Map<String, Object> model) {
        log.info("Get card with number: " + cardNumber);
        String cardNumberValid = cardNumber.replace("-", "");
        Optional<Card> card = cardRepository.findCardByCardNumberIsAndLockedIsFalse(cardNumberValid);
        if (card.isEmpty()) {
            return "redirect:/error/" + Error.WRONG_CARD_NUMBER.getCode();
        } else return "redirect:/pin/" + cardNumberValid;


    }

    @RequestMapping(value = "/pin/{cardNumber}", method = RequestMethod.GET)
    public String showPinForm(@PathVariable("cardNumber") String cardNumber, Map<String, Object> model) {
        model.put("cardNumber", cardNumber);
        return "pinForm";
    }

    @RequestMapping(value = "/pin/{cardNumber}", method = RequestMethod.POST)
    public String handlePin(@PathVariable("cardNumber") String cardNumber, @RequestParam("pin") String pin, Map<String, Object> model) {
        Optional<Card> card = cardRepository.findCardByCardNumberAndPin(cardNumber, pin);
        if (card.isEmpty()) {
            if (unsuccessfulTries == maxRetries - 1) {
                unsuccessfulTries = 0;
                Card cardWithNumber = cardRepository.findCardByCardNumber(cardNumber).get();
                cardWithNumber.setLocked(true);
                cardRepository.save(cardWithNumber);
                return "redirect:/error/+"+ Error.CARD_BLOCKED.getCode();
            }
            unsuccessfulTries++;
            return "pinForm";
        } else {
            unsuccessfulTries = 0;
            return "redirect:/card/" + card.get().getCardId() + "/operations";
        }

    }

}
