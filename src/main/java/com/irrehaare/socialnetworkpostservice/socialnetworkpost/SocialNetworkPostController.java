package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/social-network-posts")
@AllArgsConstructor
public class SocialNetworkPostController {

    @GetMapping
    public String getPosts(){
        return "Testing the endpoint";
    }
}
