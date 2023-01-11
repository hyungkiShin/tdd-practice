package com.example.javatestdriven.study;

import com.example.javatestdriven.domain.Member;
import com.example.javatestdriven.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
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
        Member member =  Member.builder()
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
        Member member =  Member.builder()
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
}