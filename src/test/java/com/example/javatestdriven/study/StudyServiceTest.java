package com.example.javatestdriven.study;

import com.example.javatestdriven.domain.Member;
import com.example.javatestdriven.domain.Study;
import com.example.javatestdriven.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository repository;

    @Test
    void createStudyService() {

        final StudyService studyService = new StudyService(memberService, repository);
        assertNotNull(studyService);
    }

    @Test
    void test_멤버_stubbing() {
        // given
        Member member = Member.builder()
                .id(1L)
                .email("tkaqkeldk@naver.com")
                .build();

        // when & then
        when(memberService.findById(1L))
                .thenReturn(Optional.of(member));

        Optional<Member> findById = memberService.findById(1L);
        assertEquals("tkaqkeldk@naver.com", findById.get().getEmail());
    }

    @Test
    @DisplayName("test 1이라는 값으로 member 서비스에 validate 를 호출하면 예외를 던지는 stubbing")
    void test_1이라는_값으로_member_서비스에_validate_를_호출하면_예외를_던지는_stubbing() {
        // given & when
        doThrow(new IllegalArgumentException())
                .when(memberService).validate(1L);
        // then
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));

        memberService.validate(2L);
    }

    @Test
    @DisplayName("테스트명")
    void 테스트명() {
        // given
        Member member = Member.builder()
                .id(1L)
                .email("tkaqkeldk@naver.com")
                .build();

        // when
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);

        // then
        assertEquals("tkaqkeldk@naver.com", byId.get().getEmail());

        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));
    }

    @Test
    @DisplayName("BDD 스타일 테스트")
    void testMockStubbingStudy() {

        final StudyService studyService = new StudyService(memberService, repository);
        assertNotNull(studyService);

        final Member member = Member.builder()
                .id(1L)
                .email("tkaqkeldk@naver.com")
                .build();


        Study study = new Study(10, "테스트");

        // given
        given(memberService.findById(1L))
                .willReturn(Optional.of(member));

        given(repository.save(study))
                .willReturn(study);

        // when
        studyService.createNewStudy(1L, study);

        // then
        assertEquals(member, study.getOwner());
        then(memberService).should(times(1)).notify(study);
        // memberService 라는 Mock 객체는 더이상 사용되지 않아야 한다.
        then(memberService).shouldHaveNoMoreInteractions();
    }

}