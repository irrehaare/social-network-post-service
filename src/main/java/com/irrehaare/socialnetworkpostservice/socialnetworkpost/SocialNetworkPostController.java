package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.NewSocialNetworkPostDto;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.OrderOption;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.SocialNetworkPost;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/social-network-posts")
@AllArgsConstructor
public class SocialNetworkPostController {

    private final SocialNetworkPostService snpService;

    // READ FUNCTIONALITIES
    @GetMapping
    @ResponseBody
    public List<SocialNetworkPost> getPosts(@RequestParam(defaultValue = "0") int pageNumber,
                                            @RequestParam(defaultValue = "50") int pageSize,
                                            @RequestParam(defaultValue = "ID") OrderOption orderOption,
                                            @RequestParam(required = false) String author){
        log.debug(String.format("Providing list of social posts. pageNum=%d, pageSize=%d, orderBy=%s, author=%s",
                pageNumber, pageSize, orderOption.columnName, Objects.requireNonNullElse(author, "any")));
        return snpService.getPosts(pageNumber, pageSize, orderOption, author);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public SocialNetworkPost getPostById(@PathVariable Long id){
        log.debug(String.format("Providing post by ID = %s", id));
        final Optional<SocialNetworkPost> maybePost = snpService.getPost(id);
        return maybePost.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Social network post not found"));
    }

    @GetMapping("/count")
    public long getPostsCount(){
        log.debug("Providing total count of posts");
        return snpService.getPostsCount();
    }

    //CREATE FUNCTIONALITIES
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public SocialNetworkPost addPost(@RequestBody NewSocialNetworkPostDto newPostDto){
        log.debug(String.format("Adding new post from %s", newPostDto.getAuthor()));
        return snpService.addPost(newPostDto);
    }

    //UPDATE FUNCTIONALITIES
}
