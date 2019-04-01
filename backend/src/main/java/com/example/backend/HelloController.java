package com.example.backend;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    @PreAuthorize("hasAuthority('SCOPE_resource.read')")
    public Message get(@AuthenticationPrincipal JwtAuthenticationToken jwtAuthentication){
        String userName = jwtAuthentication.getToken().getSubject();
        String instanceIndex = System.getenv("CF_INSTANCE_INDEX");
        return new Message("Hello user '"+ userName + "' from instance '" + instanceIndex + "' the time is " +  new Date());
    }
}
