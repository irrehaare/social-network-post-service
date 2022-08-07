package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.NewSocialNetworkPostDto;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.OrderOption;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.SocialNetworkPost;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.UpdateSocialNetworkPostDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                                            @RequestParam(required = false) String author) {
        log.debug(String.format("Providing list of social posts. pageNum=%d, pageSize=%d, orderBy=%s, author=%s",
                pageNumber, pageSize, orderOption.columnName, Objects.requireNonNullElse(author, "any")));
        return snpService.getPosts(pageNumber, pageSize, orderOption, author);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public SocialNetworkPost getPostById(@PathVariable Long id) {
        log.debug(String.format("Providing post by ID = %s", id));
        final Optional<SocialNetworkPost> maybePost = snpService.getPost(id);
        return maybePost.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Social network post not found"));
    }

    @GetMapping("/count")
    public long getPostsCount() {
        log.debug("Providing total count of posts");
        return snpService.getPostsCount();
    }

    //CREATE FUNCTIONALITIES
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public SocialNetworkPost addPost(@RequestBody NewSocialNetworkPostDto newPostDto) {
        log.debug(String.format("Adding new post from %s", newPostDto.getAuthor()));
        return snpService.addPost(newPostDto);
    }

    // UPDATE FUNCTIONALITIES
    @PutMapping("/{id}")
    public ResponseEntity<String> editPost(@RequestBody UpdateSocialNetworkPostDto updatePostDto,
                                           @PathVariable Long id) {
        log.debug(String.format("Updating the content of post ID=%d to '%s'", id, updatePostDto.getContent()));
        final SocialNetworkPost updatedPost = snpService.editPost(id, updatePostDto);
        if (updatedPost.getContent().equals(updatePostDto.getContent())) {
            return new ResponseEntity<>("Post updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update post's content.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE FUNCTIONALITIES
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id,
                                             @RequestParam(defaultValue = "false") boolean permanent){
        if (permanent){
            final HttpStatus httpStatus = snpService.permanentDelete(id);
            String responseMessage = getResponseMessage(httpStatus);
            return new ResponseEntity<>(responseMessage, httpStatus);
        } else {
            return new ResponseEntity<>("Feature not implemented yet", HttpStatus.NOT_IMPLEMENTED);
        }
    }

    private String getResponseMessage(HttpStatus httpStatus) {
        String responseMessage = "";
        switch(httpStatus) {
            case NOT_FOUND:
                responseMessage = "No post to delete";
                break;
            case INTERNAL_SERVER_ERROR:
                responseMessage = "Failed to delete the post";
                break;
            case NO_CONTENT:
                responseMessage = "Post successfully deleted";
                // TODO investigate why this message is not sent in response
                break;
            default:
                responseMessage = "Undefined error occurred";
        }
        return responseMessage;
    }
}
