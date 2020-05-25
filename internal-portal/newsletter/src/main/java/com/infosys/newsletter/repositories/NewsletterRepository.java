package com.infosys.newsletter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.newsletter.models.Newsletter;

import java.util.List;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {

	List<Newsletter> findByOrderByIdDesc();
}
