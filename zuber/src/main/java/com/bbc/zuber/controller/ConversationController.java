package com.bbc.zuber.controller;

import com.bbc.zuber.model.message.dto.MessageDto;
import com.bbc.zuber.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/conversation")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;
    private final ModelMapper modelMapper;

    @GetMapping("{rideInfoId}")
    public ResponseEntity<LinkedList<MessageDto>> findMessagesByRideInfoId(@PathVariable long rideInfoId) {
        LinkedList<MessageDto> messages = conversationService.findMessagesFromConversation(rideInfoId)
                .stream()
                .map(message -> modelMapper.map(message, MessageDto.class))
                .collect(Collectors.toCollection(LinkedList::new));
        return new ResponseEntity<>(messages, OK);
    }
}

    //todo CHAT
    //  przy getMessages zwracac tylko wiadomosci bez reszty
    // TODO: ubrac message w konwersacje

    // konwersacje prowadzi sie miedzy driverem a klientem,

    // konwersajce maja wiadomosci

    // wiadomosci w konwersacji maja swoja kolejnosc

    // kazda wiadomosc w konwersacji powinna wskazywac na poprzednia (chyba ze jest pierwsza, to wtedy poprzednia = null)

    // trzeba by do tego zaimplementowac cos na ksztalt drzewka/ do przemyslenia czy nie zastosowac gdzies jakos linkedList i na jej podstawie pokazywac wiadomosci

    // kazdy nowy przejazd to nowa konwersacja, nawet miedzy tymi samymi kierowcami/klientami

    // mozna by zrobic endpoint findByIdprzejazdu - ma zwrocic konwersacje dla przejazdu

    // ***** - specjalnie 5 gwiazdek bo najpierw chce by powyzsza logika byla gotowa^ -

    // zaimplementowac wyuszkiwanie konwersacji po kryteriach wyszukiwania za pomoca QueryDSL. Kryteria to moga byc np: kierowca, klient, zakres dat, miasto itp. Mozemy uzywac dowolnej konbinacji kryteriow, tzn moze byc jedno lub wiele
