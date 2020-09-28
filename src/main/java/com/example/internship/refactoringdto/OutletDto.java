package com.example.internship.refactoringdto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * @author Ivan Gubanov
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OutletDto {

    @Schema(description = "todo")
    @JsonView(View.All.class)
    private long id;

    @Schema(description = "todo")
    @JsonView(View.Public.class)
    @Size(max = 64)
    private String city;

    @Schema(description = "todo")
    @JsonView(View.Public.class)
    @Size(max = 64)
    private String name;

    @Schema(description = "todo")
    @JsonView(View.Public.class)
    @Size(max = 128)
    private String address;

    @Schema(description = "todo")
    @JsonView(View.Public.class)
    @Size(max = 64)
    private String phone;

    @Schema(description = "todo")
    @JsonView(View.Public.class)
    @Size(max = 32)
    private String openingHours;

    @Schema(description = "todo")
    @JsonView(View.Public.class)
    private double longitude;

    @Schema(description = "todo")
    @JsonView(View.Public.class)
    private double latitude;
}
