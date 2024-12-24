package com.company;

import lombok.*;

@Setter @Getter
@AllArgsConstructor
@ToString
@Builder
public class User {

    private long chatId;
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    
}
