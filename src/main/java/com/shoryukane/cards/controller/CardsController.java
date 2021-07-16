package com.shoryukane.cards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.shoryukane.cards.config.CardsServiceConfig;
import com.shoryukane.cards.model.Cards;
import com.shoryukane.cards.model.Customer;
import com.shoryukane.cards.model.Properties;
import com.shoryukane.cards.repository.CardsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardsController {

    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsServiceConfig cardsServiceConfig;

    @PostMapping("/myCards")
    public List<Cards> getCardDetails(@RequestHeader("shoryukane-correlation-id") String correlationId, @RequestBody Customer customer) {
        logger.info("Inside my cards method.");
        return cardsRepository.findByCustomerId(customer.getCustomerId());
    }

    @GetMapping("/cards/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(
                cardsServiceConfig.getMsg(),
                cardsServiceConfig.getBuildVersion(),
                cardsServiceConfig.getMailDetails(),
                cardsServiceConfig.getActiveBranches()
        );
        return objectWriter.writeValueAsString(properties);
    }

}
