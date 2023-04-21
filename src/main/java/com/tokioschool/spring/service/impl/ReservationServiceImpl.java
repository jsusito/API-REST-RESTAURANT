package com.tokioschool.spring.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tokioschool.spring.domain.Reservation;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.ReservationDTO;
import com.tokioschool.spring.domain.repository.ReservationDAO;
import com.tokioschool.spring.domain.repository.UserDAO;
import com.tokioschool.spring.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final int MAX_NUM_TABLES = 8;
    private final ReservationDAO reservationDAO;
    private final UserDAO userRepository;
    private final ModelMapper modelMapper;
    @Override
    public void save(ReservationDTO reservationDTO) {
        
        
        //validations
        User user = userRepository.findByUsername(reservationDTO.getUserUsername())
            .orElseThrow(() -> new NoSuchElementException("No existe ese usuario"));
        
        if (reservationDTO.getDinnerTable() == 0 && reservationDTO.getLunchTable() == 0)  
          throw new IllegalArgumentException("tienes que elegir una mesa");

        if(reservationDTO.getDinnerTable() > MAX_NUM_TABLES)  
            throw new IllegalArgumentException("Solo hay %d mesas".formatted(MAX_NUM_TABLES));
        
        if(reservationDTO.getLunchTable() > MAX_NUM_TABLES)  
            throw new IllegalArgumentException("Solo hay %d mesas".formatted(MAX_NUM_TABLES));
        
        if(reservationDTO.getLunchHour() == null && reservationDTO.getDinnerHour()==null)
            throw new IllegalArgumentException("tienes que elegir un horario");

        Reservation reservation = 
            Reservation.builder()
                .dateReservations(reservationDTO.getDateReservations())
                .dinnerHour(reservationDTO.getDinnerHour())
                .dinnerTable(reservationDTO.getDinnerTable())
                .lunchHour(reservationDTO.getLunchHour())
                .lunchTable(reservationDTO.getLunchTable())
                .numberPeople(reservationDTO.getNumberPeople())
                .user(user)
                .build();
            
        reservationDAO.save(reservation);
    }

    @Override
    public void delete(long id) {
        reservationDAO.deleteById(id);
    }

    @Override
    public List<ReservationDTO> getReservations(String surname) {
        
        return reservationDAO.findByUserUsernameOrderByDateReservations(surname)
                .stream()
                .map(reserva -> modelMapper.map(reserva, ReservationDTO.class)).toList();
    }
    
}
