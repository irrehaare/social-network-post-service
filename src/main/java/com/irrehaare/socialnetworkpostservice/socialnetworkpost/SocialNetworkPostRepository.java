package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialNetworkPostRepository extends JpaRepository<SocialNetworkPost, Long> {

}
