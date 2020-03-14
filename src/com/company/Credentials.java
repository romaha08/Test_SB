package com.company;

import lombok.Getter;
import lombok.Setter;

public class Credentials {
    @Getter @Setter
    private String host;

    @Getter @Setter
    private String userName;

    @Getter @Setter
    private String password;
}
