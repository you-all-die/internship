package com.example.internship.api;

import com.example.internship.dto.outlet.OutletDto;
import com.example.internship.entity.Outlet;
import com.example.internship.service.OutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@RestController
@RequestMapping("/api/about")
public class CityRestController {

    @Autowired
    private OutletService outletService;

    @PostMapping("")
    public Iterable<Outlet> showOutletsFromCity(@RequestParam(name = "city", required = false) String city) {
        return outletService.getOutlets(city);
    }

    @PostMapping("/coordinates")
    public List<OutletDto.Response.Coordinates> getCoordinates() {
        return outletService.getAllCoordinates();
    }
}
