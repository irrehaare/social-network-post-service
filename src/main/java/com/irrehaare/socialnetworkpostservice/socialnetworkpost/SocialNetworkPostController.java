package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/social-network-posts")
@AllArgsConstructor
public class SocialNetworkPostController {

    private final SocialNetworkPostService snpService;

    // READ FUNCTIONALITIES
    @GetMapping
    @ResponseBody
    public List<SocialNetworkPostDto> getPosts(@RequestParam(defaultValue = "0") int pageNumber,
                                               @RequestParam(defaultValue = "50") int pageSize,
                                               @RequestParam(defaultValue = "ID") OrderOption orderOption,
                                               @RequestParam(required = false) String author) {
        log.debug(String.format("Providing list of social posts. pageNum=%d, pageSize=%d, orderBy=%s, author=%s",
                pageNumber, pageSize, orderOption.columnName, Objects.requireNonNullElse(author, "any")));
        return snpService.getPosts(pageNumber, pageSize, orderOption, author);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public SocialNetworkPostDto getPostById(@PathVariable Long id) {
        log.debug(String.format("Providing post by ID = %s", id));
        return snpService.getPost(id);
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

    @PostMapping("/{id}/views-count/increment")
    public ResponseEntity<String> incrementViewCount(@PathVariable Long id){
        log.debug(String.format("Increasing the view count of post ID=%d", id));
        snpService.incrementViewCount(id);
        return new ResponseEntity<>("View count incremented.", HttpStatus.OK);
    }

    // DELETE FUNCTIONALITIES
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id,
                                             @RequestParam(defaultValue = "false") boolean permanent) {
        HttpStatus httpStatus;
        if (permanent) {
            httpStatus = snpService.permanentDelete(id);
        } else {
            httpStatus = snpService.delete(id);
        }
        String responseMessage = getDeleteResponseMessage(httpStatus);
        return new ResponseEntity<>(responseMessage, httpStatus);
    }

    private String getDeleteResponseMessage(HttpStatus httpStatus) {
        String responseMessage;
        switch (httpStatus) {
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
