package com.codingShuttle.projects.airBnbApp.service;

import com.codingShuttle.projects.airBnbApp.entity.Booking;

public interface CheckOutService {

    String getCheckOutSession(Booking booking, String successUrl , String failureUrl) ;
}
