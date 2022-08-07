package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain.SocialNetworkPost;
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

    @Query(value = "SELECT * FROM SOCIAL_NETWORK_POST WHERE AUTHOR = ?1 AND IS_DELETED=false",
            countQuery = "SELECT count(*) FROM SOCIAL_NETWORK_POST WHERE AUTHOR = ?1 AND IS_DELETED=false",
            nativeQuery = true)
    List<SocialNetworkPost> findAllByAuthorAndDeletedIsNot(String author, Pageable pageable);

    @Query(value = "SELECT * FROM SOCIAL_NETWORK_POST WHERE IS_DELETED=false",
            nativeQuery = true)
    List<SocialNetworkPost> findAllByDeletedIsNot(Pageable pageable);

    long countAllByIsDeleted(boolean isDeleted);
}
