package com.example.internship.controller.about;

import com.example.internship.service.OutletService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/about")
@Slf4j
@RequiredArgsConstructor
public class AboutController {

    private static final String CITY_FILTER_COOKIE_NAME = "cityFilterCookie";
    private static final String LONGITUDE_COOKIE_NAME = "longitudeCookie";
    private static final String LATITUDE_COOKIE_NAME = "latitudeCookie";

    @Value("${yandex.maps.version:2.1}")
    private String version;

    @Value("${yandex.maps.apikey}")
    private String apikey;

    @Value("${yandex.maps.mode:release}")
    private String mode;

    @Value("${yandex.maps.defaults.longitude:54.314192}")
    private String defaultLongitude;

    @Value("${yandex.maps.defaults.latitude:48.403123}")
    private String defaultLatitude;



    private final OutletService outletService;

    @GetMapping("")
    public String showAboutPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @NotNull Model model,
            @CookieValue(name = CITY_FILTER_COOKIE_NAME, required = false, defaultValue = "") String cityFilter,
            @CookieValue(name = LONGITUDE_COOKIE_NAME, required = false, defaultValue = "") String longitude,
            @CookieValue(name = LATITUDE_COOKIE_NAME, required = false, defaultValue = "") String latitude
    ) {
        /* Проверить наличие нужной куки и создать, если отсутствует */
        Optional<Cookie> cityFilterCookie = Arrays.stream(request.getCookies()).filter(
                cookie -> cookie.getName().equals(CITY_FILTER_COOKIE_NAME)
        ).findFirst();
        if (cityFilterCookie.isEmpty()) {
            response.addCookie(new Cookie(CITY_FILTER_COOKIE_NAME, cityFilter));
        }

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
        model.addAttribute("outlets", outletService.getAll());
        model.addAttribute("version", version);
        model.addAttribute("apikey", apikey);
        model.addAttribute("mode", mode);
        return "about/index";
    }
}
