package com.codingShuttle.projects.airBnbApp.controller;

import com.codingShuttle.projects.airBnbApp.dto.RoomDto;
import com.codingShuttle.projects.airBnbApp.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor // It will make constructor for me
public class RoomAdminController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId,
                                                 @RequestBody RoomDto roomDto) {
        RoomDto room = roomService.createNewRoom(hotelId, roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(roomService.getAllRoomsInHotel(hotelId));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long hotelId, @PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<RoomDto> deleteRoomById(@PathVariable Long hotelId, @PathVariable Long roomId) {
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

}
