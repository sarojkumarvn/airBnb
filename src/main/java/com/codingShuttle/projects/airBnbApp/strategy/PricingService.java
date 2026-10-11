package com.codingShuttle.projects.airBnbApp.strategy;

import com.codingShuttle.projects.airBnbApp.entity.Inventory;

import java.math.BigDecimal;

public class PricingService {



    public BigDecimal calculateDynamicPricingStrategy(Inventory inventory) {
        PricingStrategy pricingStrategy = new BasePricingStrategy() ;
        pricingStrategy = new SurgePricingStrategy(pricingStrategy) ;
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy) ;
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy) ;
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy) ;


        return pricingStrategy.calculatePrice(inventory)  ;

    }
}
