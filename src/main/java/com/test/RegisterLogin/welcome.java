package com.test.RegisterLogin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class welcome {
    @PostMapping(path ="/hii")
    public String returnhii1() {

        return "hii id";
    }
}
