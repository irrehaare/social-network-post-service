package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/social-network-posts")
@AllArgsConstructor
public class SocialNetworkPostController {

    private final SocialNetworkPostService snpService;

    @GetMapping
    @ResponseBody
    public List<SocialNetworkPost> getPosts(@RequestParam(defaultValue = "0") int pageNumber,
                                            @RequestParam(defaultValue = "50") int pageSize,
                                            @RequestParam(defaultValue = "ID") OrderOption orderOption){
        log.debug("Providing list of social posts");
        return snpService.getPosts(pageNumber, pageSize, orderOption);
    }

    @GetMapping("/count")
    public long getPostsCount(){
        log.debug("Providing total count of posts");
        return snpService.getPostsCount();
    }
}
