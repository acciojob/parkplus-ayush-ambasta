package com.driver.services.impl;

import com.driver.model.ParkingLot;

import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        return parkingLotRepository1.save(parkingLot);
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {


        Spot spot = new Spot();

        if (numberOfWheels <= 2) {
            spot.setSpotType(SpotType.TWO_WHEELER);
        }else if (numberOfWheels <= 4){
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }else{
            spot.setSpotType(SpotType.OTHERS);
        }

        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();

        spot.setPricePerHour(pricePerHour);
        spot.setParkingLot(parkingLot);

        parkingLot.getSpotList().add(spot);

        parkingLotRepository1.save(parkingLot);

        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {
//
//        Optional<Spot> optionalSpot = spotRepository1.findById(spotId);
//        if(optionalSpot.isEmpty()){
//            return;
//        }
//        Spot spot = optionalSpot.get();
//        ParkingLot parkingLot = spot.getParkingLot();
//        parkingLot.getSpotList().remove(spot);
//        spotRepository1.deleteById(spotId);
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository1.findById(parkingLotId);
        if(optionalParkingLot.isEmpty()){
            return null;
        }
        ParkingLot parkingLot = optionalParkingLot.get();
        for(Spot spot : parkingLot.getSpotList()){
            if(spot.getId() == spotId){
                spot.setPricePerHour(pricePerHour);
                spotRepository1.save(spot);
                return spot;
            }
        }
        return null;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
//        Optional<ParkingLot> optionalParkingLot = parkingLotRepository1.findById(parkingLotId);
//        if(optionalParkingLot.isEmpty()) return;
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
