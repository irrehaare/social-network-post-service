package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import com.irrehaare.socialnetworkpostservice.posteditaudit.PostEditAuditService;
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
    private final PostEditAuditService peaService;

    // READ FUNCTIONALITIES

    // TODO Refactor this method later - it's too long and has code repeating
    public List<SocialNetworkPostDto> getPosts(int pageNumber,
                                               int pageSize,
                                               OrderOption orderOption,
                                               String author,
                                               boolean shouldIncrementViews) {
        final Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderOption.columnName));
        if (author != null) {
            return snpRepository.findAllByAuthorAndDeletedIsNot(author, pageRequest)
                    .parallelStream()
                    .map(p -> {
                        if (shouldIncrementViews){
                            incrementViewCount(p.getId(), 1);
                        }
                        return new SocialNetworkPostDto(p);
                    })
                    .collect(Collectors.toList());
        } else {
            return snpRepository.findAllByDeletedIsNot(pageRequest)
                    .parallelStream()
                    .map(p -> {
                        if (shouldIncrementViews){
                            incrementViewCount(p.getId(), 1);
                        }
                        return new SocialNetworkPostDto(p);
                    })
                    .collect(Collectors.toList());
        }
    }

    public long getPostsCount() {
        return snpRepository.countAllByIsDeleted(false);
    }

    public SocialNetworkPostDto getPost(Long id, boolean shouldIncrementViews) {
        final SocialNetworkPost post = snpRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Social network post not found"));
        if (post.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Social network post has been deleted");
        }

        if (shouldIncrementViews){
            incrementViewCount(id, 1);
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

        peaService.addEdit(postToEdit);

        return snpRepository.save(new SocialNetworkPost(
                postToEdit.getId(),
                postToEdit.getPostDate(),
                postToEdit.getAuthor(),
                updatePostDto.getContent(),
                postToEdit.getViewCount(),
                postToEdit.isDeleted())
        );
    }

    public void incrementViewCount(final Long id, final Integer by) {
        final Optional<SocialNetworkPost> maybePost = snpRepository.findById(id);
        if (maybePost.isEmpty() || maybePost.get().isDeleted()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        final SocialNetworkPost post = maybePost.get();
        snpRepository.save(new SocialNetworkPost(
                post.getId(),
                post.getPostDate(),
                post.getAuthor(),
                post.getContent(),
                post.getViewCount()+by,
                post.isDeleted())
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
