package com.codingShuttle.projects.airBnbApp.service;


import com.codingShuttle.projects.airBnbApp.entity.Booking;
import com.codingShuttle.projects.airBnbApp.entity.User;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CheckOutServiceImpl implements CheckOutService{




    @Override
    public String getCheckOutSession(Booking booking, String successUrl, String failureUrl) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;


        try {
            CustomerCreateParams customerParams = CustomerCreateParams.builder().setName(user.getName())
                    .setEmail(user.getEmail())
                    .build() ;



            Customer customer = Customer.create(customerParams);

        SessionCreateParams params = SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                                .setCustomer(customer.getId())
                                        .setSuccessUrl(successUrl)
                                                .setCancelUrl(failureUrl)
                                                        .build() ;

        } catch(Exception e) {
            throw new RuntimeException(e) ;
        }



        return "";
    }
}
