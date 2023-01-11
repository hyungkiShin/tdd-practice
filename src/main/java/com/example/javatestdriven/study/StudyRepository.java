package com.example.javatestdriven.study;

import com.example.javatestdriven.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
