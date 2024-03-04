package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        Optional<User> optionalUser = userRepository3.findById(userId);
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository3.findById(parkingLotId);
        if(optionalUser.isEmpty() || optionalParkingLot.isEmpty()){
            throw new Exception("Cannot make reservation");
        }
        User user = optionalUser.get();
        Spot spot = getSpot(numberOfWheels, optionalParkingLot);
        if(spot==null){
            throw new Exception("Cannot make reservation");
        }
        spot.setOccupied(Boolean.TRUE);
        Reservation reservation = new Reservation();
        reservation.setSpot(spot);
        reservation.setNumberOfHours(timeInHours);
        reservation.setUser(user);

        user.getReservationList().add(reservation);
        spot.getReservationList().add(reservation);

        spotRepository3.save(spot);
        userRepository3.save(user);
        return reservation;
    }

    private static Spot getSpot(Integer numberOfWheels, Optional<ParkingLot> optionalParkingLot) {
        ParkingLot parkingLot = optionalParkingLot.get();
        List<Spot> spotList = parkingLot.getSpotList();
        int price = Integer.MAX_VALUE;
        Spot spot = null;
        for(Spot s: spotList){
            int wheels = 0;
            if(s.getSpotType()==SpotType.TWO_WHEELER){
                wheels=2;
            }else if(s.getSpotType()==SpotType.FOUR_WHEELER){
                wheels=4;
            }else{
                wheels = 1000;
            }
            if(wheels>= numberOfWheels && !s.getOccupied() && price>s.getPricePerHour()){
                spot=s;
                price=s.getPricePerHour();
            }
        }
        return spot;
    }
}
