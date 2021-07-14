package com.shoryukane.cards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.shoryukane.cards.config.CardsServiceConfig;
import com.shoryukane.cards.model.Cards;
import com.shoryukane.cards.model.Customer;
import com.shoryukane.cards.model.Properties;
import com.shoryukane.cards.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsServiceConfig cardsServiceConfig;

    @PostMapping("/myCards")
    public List<Cards> getCardDetails(@RequestBody Customer customer) {
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
