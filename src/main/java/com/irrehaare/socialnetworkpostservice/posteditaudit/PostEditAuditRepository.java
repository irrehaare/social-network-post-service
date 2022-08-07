package com.irrehaare.socialnetworkpostservice.posteditaudit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEditAuditRepository extends JpaRepository<PostEditAudit, Long> {

}
