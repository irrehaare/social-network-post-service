package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
                                            @RequestParam(defaultValue = "ID") OrderOption orderOption,
                                            @RequestParam(required = false) String author){
        log.debug("Providing list of social posts");
        return snpService.getPosts(pageNumber, pageSize, orderOption, author);
    }

    @GetMapping("/count")
    public long getPostsCount(){
        log.debug("Providing total count of posts");
        return snpService.getPostsCount();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public SocialNetworkPost getPostById(@PathVariable Long id){
        log.debug(String.format("Providing post by ID = %s", id));
        final Optional<SocialNetworkPost> maybePost = snpService.getPost(id);
        return maybePost.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Social network post not found"));
    }
}
