package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class SocialNetworkPostService {
    private final SocialNetworkPostRepository snpRepository;

    public List<SocialNetworkPost> getPosts(int pageNumber, int pageSize, OrderOption orderOption){
        return snpRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }
}
