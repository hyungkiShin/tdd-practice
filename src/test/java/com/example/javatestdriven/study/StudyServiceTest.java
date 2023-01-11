package com.example.javatestdriven.study;

import com.example.javatestdriven.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository repository;

    @Test
    void createStudyService() {

        // Mockito.mock() 메소드 만드는 방법
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);

        final StudyService studyService = new StudyService(memberService, repository);
        assertNotNull(studyService);
    }

}