package com.example.internship.service.outlet;

import com.example.internship.dto.outlet.OutletDto;
import com.example.internship.entity.Outlet;
import com.example.internship.repository.OutletRepository;
import com.example.internship.service.OutletService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Service
@AllArgsConstructor
public class OutletServiceImpl implements OutletService {

    private final OutletRepository outletRepository;
    private final ModelMapper modelMapper;

    public List<OutletDto.Response.Full> getAll() {
        return outletRepository.findAll().stream()
                .map(this::convertToFullDto)
                .collect(Collectors.toList());
    }

    public Optional<Outlet> getById(long id) {
        return outletRepository.findById(id);
    }

    public void save(Outlet outlet) {
        outletRepository.save(outlet);
    }

    public void delete(long id) {
        outletRepository.deleteById(id);
    }

    public List<OutletDto.Response.OnlyCities> getCities() {
        return outletRepository.findCities().stream()
                .map(this::convertToOnlyCitiesDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public Iterable<Outlet> getOutlets(String city) {
        if (null == city || city.isEmpty()) {
            return outletRepository.findAll();
        }
        return outletRepository.findAllByCity(city);
    }

    public List<OutletDto.Response.OnlyCoordinates> getAllCoordinates() {
        return outletRepository.findAll().stream()
                .map(this::convertToOnlyCoordinatesDto)
                .collect(Collectors.toUnmodifiableList());
    }

    private OutletDto.Response.OnlyCoordinates convertToOnlyCoordinatesDto(Outlet outlet) {
        return modelMapper.map(outlet, OutletDto.Response.OnlyCoordinates.class);
    }

    private OutletDto.Response.Full convertToFullDto(Outlet outlet) {
        return modelMapper.map(outlet, OutletDto.Response.Full.class);
    }

    private OutletDto.Response.OnlyCities convertToOnlyCitiesDto(String city) {
        return modelMapper.map(city, OutletDto.Response.OnlyCities.class);
    }
}
