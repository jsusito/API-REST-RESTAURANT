package com.tokioschool.spring.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokioschool.spring.domain.dto.GuestReservationDTO;
import com.tokioschool.spring.domain.dto.ReservationDTO;
import com.tokioschool.spring.domain.dto.ReservationUserAndGuest;
import com.tokioschool.spring.service.GuestReservationService;
import com.tokioschool.spring.service.ReservationService;
import com.tokioschool.spring.service.ReservationUserAndGuestService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@Slf4j
public class ControllerReservation {
    
    private final ReservationService reservationService;
    private final GuestReservationService guestReservationService;
    private final ReservationUserAndGuestService reservationUserAndGuestService;


    @GetMapping("/{surname}")
    public ResponseEntity<List<ReservationDTO>> getReservations(@PathVariable String surname){
        return ResponseEntity.ok()
            .body(reservationService.getReservationsByUsername(surname));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<ReservationUserAndGuest>> getReservationsByDate(@PathVariable LocalDate date){
        return ResponseEntity.ok()
            .body(reservationUserAndGuestService.getReservations(date));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid ReservationDTO reservationDTO){
        
        reservationService.save(reservationDTO);
        return ResponseEntity.ok("");
    }
    
    @PostMapping("/guest")
    public ResponseEntity<?> addGuestReservations(@RequestBody @Valid GuestReservationDTO guestReservationDTO){
        
        try {
            new Thread().sleep(3000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());      
        }
        
        guestReservationService.save(guestReservationDTO);
        return ResponseEntity.ok("");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotNull long id){
        
        reservationService.delete(id);
        return ResponseEntity.ok("");
    }
}
