package com.benito.employee.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    @ApiModelProperty(value = "This is the first name", required = true)
    private String firstName;

    @ApiModelProperty(value = "This is the last name", required = true)
    private String lastName;

    @ApiModelProperty(value = "Employee email address", required = false)
    private String email;

}
