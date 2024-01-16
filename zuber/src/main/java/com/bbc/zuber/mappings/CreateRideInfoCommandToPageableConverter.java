package com.bbc.zuber.mappings;

import com.bbc.zuber.model.rideinfo.command.CreateRideInfoCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CreateRideInfoCommandToPageableConverter implements Converter<CreateRideInfoCommand, Pageable> {
    @Override
    public Pageable convert(MappingContext<CreateRideInfoCommand, Pageable> mappingContext) {
        CreateRideInfoCommand rideInfoPage = mappingContext.getSource();
        return PageRequest.of(rideInfoPage.getPageNumber(),
                rideInfoPage.getPageSize(),
                Sort.by(Sort.Direction.valueOf(rideInfoPage.getSortDirection().toUpperCase()), rideInfoPage.getSortBy()));
    }
}