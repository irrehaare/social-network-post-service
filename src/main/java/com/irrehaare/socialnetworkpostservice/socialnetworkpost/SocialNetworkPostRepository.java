package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface SocialNetworkPostRepository extends JpaRepository<SocialNetworkPost, Long> {

    @Query(value = "SELECT * FROM SOCIAL_NETWORK_POST WHERE AUTHOR = ?1",
        countQuery = "SELECT count(*) FROM SOCIAL_NETWORK_POST WHERE AUTHOR = ?1",
        nativeQuery = true)
    List<SocialNetworkPost> findAllByAuthor(String author, Pageable pageable);
}
