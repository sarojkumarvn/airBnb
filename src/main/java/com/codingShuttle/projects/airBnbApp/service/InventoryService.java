package com.codingShuttle.projects.airBnbApp.service;

import com.codingShuttle.projects.airBnbApp.entity.Room;


public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);

}