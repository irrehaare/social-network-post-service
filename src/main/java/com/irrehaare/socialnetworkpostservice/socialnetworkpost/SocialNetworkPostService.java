package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class SocialNetworkPostService {
    private final SocialNetworkPostRepository snpRepository;

    // READ FUNCTIONALITIES
    public List<SocialNetworkPostDto> getPosts(int pageNumber, int pageSize, OrderOption orderOption, String author) {
        final Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderOption.columnName));
        if (author != null) {
            return snpRepository.findAllByAuthorAndDeletedIsNot(author, pageRequest)
                    .parallelStream()
                    .map(SocialNetworkPostDto::new)
                    .collect(Collectors.toList());
        } else {
            return snpRepository.findAllByDeletedIsNot(pageRequest)
                    .parallelStream()
                    .map(SocialNetworkPostDto::new)
                    .collect(Collectors.toList());
        }
    }

    public long getPostsCount() {
        return snpRepository.countAllByIsDeleted(false);
    }

    public SocialNetworkPostDto getPost(Long id) {
        final SocialNetworkPost post = snpRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Social network post not found"));
        if (post.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Social network post has been deleted");
        }
        return new SocialNetworkPostDto(post);
    }

    // CREATE FUNCTIONALITIES
    public SocialNetworkPost addPost(NewSocialNetworkPostDto newPostDto) {
        return snpRepository.save(new SocialNetworkPost(newPostDto.getAuthor(), newPostDto.getContent()));
    }

    // UPDATE FUNCTIONALITIES
    public SocialNetworkPost editPost(long id, UpdateSocialNetworkPostDto updatePostDto) {
        final SocialNetworkPost postToEdit = snpRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return snpRepository.save(new SocialNetworkPost(
                postToEdit.getId(),
                postToEdit.getPostDate(),
                postToEdit.getAuthor(),
                updatePostDto.getContent(),
                postToEdit.getViewCount(),
                postToEdit.isDeleted())
        );
    }

    // DELETE FUNCTIONALITIES
    public HttpStatus permanentDelete(Long id) {
        if (!snpRepository.existsById(id)) {
            return HttpStatus.NOT_FOUND;
        }

        snpRepository.deleteById(id);
        if (!snpRepository.existsById(id)) {
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus delete(Long id) {
        final Optional<SocialNetworkPost> maybePost = snpRepository.findById(id);
        if (maybePost.isEmpty() || maybePost.get().isDeleted()){
            return HttpStatus.NOT_FOUND;
        }

        final SocialNetworkPost post = maybePost.get();
        final SocialNetworkPost deletedPost = snpRepository.save(new SocialNetworkPost(
                post.getId(),
                post.getPostDate(),
                post.getAuthor(),
                post.getContent(),
                post.getViewCount(),
                true)
        );
        if (deletedPost.isDeleted()) {
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
