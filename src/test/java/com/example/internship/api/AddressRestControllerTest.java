package com.example.internship.api;

import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author Роман Каравашкин
 */

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class AddressRestControllerTest {

    /**
     * Интегрированое тестирование AddressRestControllerTest
     * Тест: поиск с незадаными параметрами
     * Тест: поиск с задаными параметрами
     *
     */

    AddressService addressService = mock(AddressService.class);
    AddressDto addressDtoOne = mock(AddressDto.class);

    
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public  void setUp(){

        AddressRestController addressRestController = new AddressRestController(addressService);

        mockMvc = MockMvcBuilders.standaloneSetup(addressRestController).build();
    }

    /**
     * Проверка метода getAllById
     * Возвращает объект аддрессДто по значению userId
     */

    @Test
    public void testGetAddressByUserId() throws Exception{

    addressDtoOne = createNewAddressRequest(1L,1L,"Ulyanovsk");

    Long id     =   addressDtoOne.getCustomerId();

    when(addressService.getAllById(anyLong())).thenReturn(Arrays.asList(addressDtoOne));

    mockMvc.perform(get("/api/user/{id}/address",id)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id",is(1)))
            .andExpect(jsonPath("$.customerId",is(1)))
            .andExpect(jsonPath("$.region",is("Ulyanovsk")))
            .andExpect(jsonPath("$.city",is("City1")))
            .andExpect(jsonPath("$.district",is("district1")))
            .andExpect(jsonPath("$.street",is("street1")))
            .andExpect(jsonPath("$.house",is("1")))
            .andExpect(jsonPath("$.apartment",is("11")))
            .andExpect(jsonPath("$.comment",is("test1")));

    verify(addressService,times(2)).getAllById(1L);
    verifyNoMoreInteractions(addressService);
    }


    private AddressDto createNewAddressRequest(Long id, Long customerId,String region){
        addressDtoOne = new AddressDto();
        addressDtoOne.setId(id);
        addressDtoOne.setCustomerId(customerId);
        addressDtoOne.setRegion(region);
        addressDtoOne.setCity("City1");
        addressDtoOne.setDistrict("district1");
        addressDtoOne.setStreet("street1");
        addressDtoOne.setHouse("1");
        addressDtoOne.setApartment("11");
        addressDtoOne.setComment("test1");
        return addressDtoOne;

    }

}


//    @Test
//    public void test() throws Exception{
//        for (int i =1; i<10;i++){
//            String urlTemplates = "/api/user/"+i+"/address";
//            this.mockMvc.perform(get(urlTemplates))
//                    .andDo(print())
//                    .andExpect(status().isOk())
//                    .andExpect(content().string(containsString((String.valueOf(i)))));
//        }
//    }

