package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.NewSocialNetworkPostDto;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.OrderOption;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.SocialNetworkPost;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.UpdateSocialNetworkPostDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class SocialNetworkPostService {
    private final SocialNetworkPostRepository snpRepository;

    // READ FUNCTIONALITIES
    public List<SocialNetworkPost> getPosts(int pageNumber, int pageSize, OrderOption orderOption, String author){
        final Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderOption.columnName));
        if (author != null){
            return snpRepository.findAllByAuthor(author, pageRequest);
        } else {
            return snpRepository.findAll(pageRequest).getContent();
        }
    }

    public long getPostsCount() {
        return snpRepository.count();
    }

    public Optional<SocialNetworkPost> getPost(Long id) {
        return snpRepository.findById(id);
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
                postToEdit.getViewCount()));
    }

    // DELETE FUNCTIONALITIES
    public HttpStatus permanentDelete(Long id) {
        if (!snpRepository.existsById(id)){
            return HttpStatus.NOT_FOUND;
        }

        snpRepository.deleteById(id);
        if (!snpRepository.existsById(id)) {
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
