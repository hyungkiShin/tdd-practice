package com.example.javatestdriven.member;

import com.example.javatestdriven.domain.Member;
import com.example.javatestdriven.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member newMember);
}