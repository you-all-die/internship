package com.example.internship.refactoringdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * @author Ivan Gubanov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OutletDto {

    @Schema(description = "todo")
    private long id;

    @Schema(description = "todo")
    @Size(max = 64)
    private String city;

    @Schema(description = "todo")
    @Size(max = 64)
    private String name;

    @Schema(description = "todo")
    @Size(max = 128)
    private String address;

    @Schema(description = "todo")
    @Size(max = 64)
    private String phone;

    @Schema(description = "todo")
    @Size(max = 32)
    private String openingHours;

    @Schema(description = "todo")
    private double longitude;

    @Schema(description = "todo")
    private double latitude;
}
