package com.ndourcodeur.userservice.payload;

import com.ndourcodeur.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateUserWithCarAndLapTop {

    private User user;
    private Car car;
    private LapTop lapTop;
}
