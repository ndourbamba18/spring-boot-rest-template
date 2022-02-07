package com.ndourcodeur.userservice.payload;

import com.ndourcodeur.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateUserWithLapTop {

    private User user;
    private LapTop lapTop;
}
