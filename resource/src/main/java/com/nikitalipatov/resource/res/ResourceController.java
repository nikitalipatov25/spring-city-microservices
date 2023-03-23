package com.nikitalipatov.resource.res;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/resource/user")
    public String getResourceU() {
        return "Resource User";
    }

    @GetMapping("/resource/admin")
    public String getResourceA() {
        return "Resource Admin";
    }

    @GetMapping("/resource/just")
    public String getResource() {
        return "Resource";
    }
}