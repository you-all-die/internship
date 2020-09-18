package com.example.internship.controller.about;

import com.example.internship.service.OutletService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping(AboutController.BASE_MAPPING)
@Slf4j
@RequiredArgsConstructor
public class AboutController {

    public static final String BASE_MAPPING = "/about";

    private static final String CITY_FILTER_COOKIE_NAME = "cityFilterCookie";
    private static final String LONGITUDE_COOKIE_NAME = "longitudeCookie";
    private static final String LATITUDE_COOKIE_NAME = "latitudeCookie";

    @Value("${yandex.maps.version:2.1}")
    private String version;

    @Value("${yandex.maps.apikey:dummer-kopf}")
    private String apikey;

    @Value("${yandex.maps.mode:release}")
    private String mode;

    @Value("${yandex.maps.defaults.longitude:54.314192}")
    private String defaultLongitude;

    @Value("${yandex.maps.defaults.latitude:48.403123}")
    private String defaultLatitude;



    private final OutletService outletService;

    @GetMapping
    public String showAboutPage(
            HttpServletResponse response,
            @NonNull Model model,
            @CookieValue(name = CITY_FILTER_COOKIE_NAME, required = false, defaultValue = "") String cityFilter,
            @CookieValue(name = LONGITUDE_COOKIE_NAME, required = false, defaultValue = "") String longitude,
            @CookieValue(name = LATITUDE_COOKIE_NAME, required = false, defaultValue = "") String latitude
    ) {
        /* Куки с координатами не бывает пустой */
        if (longitude.isBlank()) {
            longitude = defaultLongitude;
            response.addCookie(new Cookie(LONGITUDE_COOKIE_NAME, longitude));
        }
        if (latitude.isBlank()) {
            latitude = defaultLatitude;
            response.addCookie(new Cookie(LATITUDE_COOKIE_NAME, latitude));
        }

        model.addAttribute("cityFilter", cityFilter);
        model.addAttribute("longitude", longitude);
        model.addAttribute("latitude", latitude);
        model.addAttribute("cities", outletService.getCities());
        model.addAttribute("outlets", outletService.getOutlets());
        model.addAttribute("version", version);
        model.addAttribute("apikey", apikey);
        model.addAttribute("mode", mode);
        return BASE_MAPPING + "/index";
    }
}
