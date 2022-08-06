package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/social-network-posts")
@AllArgsConstructor
public class SocialNetworkPostController {

    private final SocialNetworkPostService snpService;

    @GetMapping
    public List<SocialNetworkPostDto> getPosts(){
        log.debug("Providing list of social posts");
        return snpService.getPosts(0, 50, OrderOption.ID);
    }
}
