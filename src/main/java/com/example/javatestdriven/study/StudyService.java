package com.example.javatestdriven.study;

import com.example.javatestdriven.domain.Member;
import com.example.javatestdriven.domain.Study;
import com.example.javatestdriven.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        if (memberService == null || repository == null) {
            throw new IllegalArgumentException("memberService or repository must not be null");
        }
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        final Optional<Member> member = memberService.findById(memberId);

        study.setOwner(
                member.orElseThrow(() ->
                        new IllegalArgumentException("Member doesn't exist for id '" + memberId + "'"))
        );

        Study newStudy = repository.save(study);

        // study 에 알림을 준다
        memberService.notify(newStudy);

        // member 한테 알림을 준다
        memberService.notify(member.get());

        return repository.save(study);
    }
}
