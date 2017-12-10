package com.fsd.supportservices.authserver.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
public class UserDTO {
    @ApiModelProperty(notes = "id of the user")
    private Integer id;
    @ApiModelProperty(notes = "username")
    private String username;
    @ApiModelProperty(notes = "useremail")
    private String useremail;
    @ApiModelProperty(notes = "password")
    private String password;
    @ApiModelProperty(notes = "role")
    private String role;
    @ApiModelProperty(notes = "createdAt")
    private Date createdAt;
    @ApiModelProperty(notes = "updatedAt")
    private Date updatedAt;
}
