package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CustomerDTO extends AbstractDTO{
    @NotBlank(message = "fullname can be not blank")
    private String fullName;
    @NotBlank(message = "phone can be not blank")
    private String phone;
    @NotBlank(message = "email can be not blank")
    private String email;
    @NotBlank(message = "demand can be not blank")
    private String demand;
    @NotBlank(message = "status can be not blank")
    private String status;
    private Integer isActive;
    private String companyName;
}
