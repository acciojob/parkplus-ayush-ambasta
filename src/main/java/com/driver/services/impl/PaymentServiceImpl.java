package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        int bill = reservation.getSpot().getPricePerHour()*reservation.getNumberOfHours();
        if(bill>amountSent){
            throw new Exception("Insufficient Amount");
        }
        Payment payment = new Payment();
        String mode1 = mode.toUpperCase();
        switch (mode1) {
            case "CARD":
                payment.setPaymentMode(PaymentMode.CARD);
                break;
            case "CASH":
                payment.setPaymentMode(PaymentMode.CASH);
                break;
            case "UPI":
                payment.setPaymentMode(PaymentMode.UPI);
                break;
            default:
                throw new Exception("Payment mode not detected");
        }
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);
        reservationRepository2.save(reservation);
        return payment;
    }
}
