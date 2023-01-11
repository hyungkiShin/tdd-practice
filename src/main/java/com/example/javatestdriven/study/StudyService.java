package com.example.javatestdriven.study;

import com.example.javatestdriven.domain.Member;
import com.example.javatestdriven.domain.Study;
import com.example.javatestdriven.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        final Optional<Member> member = memberService.findById(memberId);

        study.setOwner(
                member.orElseThrow(() ->
                        new IllegalArgumentException("Member doesn't exist for id '" + memberId + "'"))
        );
        return repository.save(study);
    }
}
