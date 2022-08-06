package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialNetworkPostService {
    public List<SocialNetworkPostDto> getPosts(int offset, int limit, OrderOption orderOption){
        return List.of();
    }
}
