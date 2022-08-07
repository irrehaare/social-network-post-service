package com.irrehaare.socialnetworkpostservice.posteditaudit;

import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.SocialNetworkPost;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class PostEditAuditService {
    private final PostEditAuditRepository peaRepository;

    public void addEdit(SocialNetworkPost oldPost){
        peaRepository.save(new PostEditAudit(oldPost.getId(), oldPost.getContent()));
    }
}
