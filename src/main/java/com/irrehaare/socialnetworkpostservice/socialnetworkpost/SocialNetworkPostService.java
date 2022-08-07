package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.NewSocialNetworkPostDto;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.OrderOption;
import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.SocialNetworkPost;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

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

    //CREATE FUNCTIONALITIES
    public SocialNetworkPost addPost(NewSocialNetworkPostDto newPostDto) {
        return snpRepository.save(new SocialNetworkPost(newPostDto.getAuthor(), newPostDto.getContent()));
    }
}
