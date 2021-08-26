package ru.dataart.cashMachine.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.dataart.cashMachine.entities.Card;

import java.util.Optional;

public interface CardRepository extends CrudRepository<Card,Long> {

    @Query("select c from Card c where c.cardNumber = ?1 and c.isLocked = false")
    Optional<Card> findCardByCardNumberIsAndLockedIsFalse(String cardNumber);

    Optional<Card> findCardByCardNumberAndPin(String cardNumber,String pin);

    Optional<Card> findCardByCardNumber(String cardNumber);

    @Modifying(clearAutomatically = true)
    @Query("update Card c set c.isLocked = true where c.cardId= ?1")
    void blockCard(@Param(value="id") Long cardId);
}
