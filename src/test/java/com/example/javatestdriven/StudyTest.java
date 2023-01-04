package com.example.javatestdriven;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

// DisplayNameGenerator 중 ReplaceUnderscores 전략으로 모든 테스트를 _ 언더스코어가 있다면 모두 공백으로 바꿔주겠다.
// @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 그러나 DisplayName 으로 쓰는걸 추천.
@Slf4j
class StudyTest {

    @Test
    @DisplayName("assert 중 현재 진행중인 테스트가 실패하면 다음으로 넘어가지 않는다")
    void study_test() {
        Study study = new Study(-10);
        assertNotNull(study);
        // 현재값, 기대값, 메세지

        // v1
        assertEquals(study.getStatus(), StudyStatus.DRAFT, "스터디를 처음 만들면 상태값이 DRAFT 여야 한다.");

        // v2 연산이 필요한 경우 lambda 로 넘겨준다. ( 테스가 성공하던 실패하던 상관없이 문자열 연산 비용이 들어간다 ) 만약 뻔한 에러 메세지 라면 위와 같이 사용하는걸 추천한다.
        assertEquals(study.getStatus(), StudyStatus.DRAFT, () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + " 여야 한다.");

        // supplier 는 람다식으로 만들어진 함수형 인터페이스
        assertEquals(study.getStatus(), StudyStatus.DRAFT, new Supplier<String>() {
            @Override
            public String get() {
                return "스터디를 처음 만들면 상태값이 DRAFT 여야 한다.";
            }
        });

        assertTrue(1 < 2);

        assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.");
    }

    @Test
    @DisplayName("assertAll 예제")
    void study_test2() {
        Study study = new Study(-10);

        // assertAll 은 모든 테스트를 실행하고 결과를 한번에 볼 수 있다.
        assertAll(
                () -> assertNotNull(study),
                // v1 메세지 연산이 없는 경우
                () -> assertEquals(study.getStatus(), StudyStatus.DRAFT, "스터디를 처음 만들면 상태값이 DRAFT 여야 한다."),

                // v2 연산이 필요한 경우 lambda 로 넘겨준다. ( 테스가 성공하던 실패하던 상관없이 문자열 연산 비용이 들어간다 ) 만약 뻔한 에러 메세지 라면 위와 같이 사용하는걸 추천한다.
                () -> assertEquals(study.getStatus(), StudyStatus.DRAFT, () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + " 여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
        );

    }

    @Test
    @DisplayName("예외처리 메세지를 뽑아내서 결과비교")
    void study_test3() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야 한다.", message);
    }

    @Test
    @DisplayName("특정한 시간안에 실행이 완료되는지 테스트한다.")
    void timeoutCompTest() {
        assertTimeout(Duration.ofMillis(300), () -> {
            new Study(10);
            Thread.sleep(295);
        });
    }

    @Test
    @DisplayName("특정한 시간안에 실행이 완료되는지 즉각적으로 테스트한다.")
    void immediatelyCompTest() {
        assertTimeoutPreemptively(Duration.ofMillis(300), () -> {
            new Study(10);
            Thread.sleep(295);
        });
    }

    @Test
    @DisplayName("테스트명")
    void studyTest() {
        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    @Test
    @DisplayName("특정한 조건에 따라 테스트를 실행하거나 실행하지 말아야 한다 1")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void studyAssumeTest1() {

        // vi ~/.zshrc 에 TEST_ENV 를 LOCAL 로 설정해주고 IntelliJ 를 다시 껐다 켜야 함. ( Intellij App 이 로딩될때 System 설정을 읽어오기 때문)
        String test_env = System.getenv("TEST_ENV");
        log.info("test_env : {}", test_env);

        // assumeTrue 는 조건이 true 일때만 테스트를 실행한다.
        assumeTrue("LOCAL".equals(test_env));
        Study study = new Study(100);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    @Test
    @DisplayName("특정한 조건에 따라 테스트를 실행하거나 실행하지 말아야 한다2")
    @DisabledOnOs({OS.MAC, OS.LINUX})
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10})
    void studyAssumeTest2() {
        // vi ~/.zshrc 에 TEST_ENV 를 LOCAL 로 설정해주고 IntelliJ 를 다시 껐다 켜야 함. ( Intellij App 이 로딩될때 System 설정을 읽어오기 때문)
        String test_env = System.getenv("TEST_ENV");
        log.info("test_env : {}", test_env);

        // assumeTrue 는 조건이 true 일때만 테스트를 실행한다.
        assumeTrue("LOCAL".equals(test_env));
        Study study = new Study(100);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    @Test
    @DisplayName("특정한 조건에 따라 테스트를 실행하거나 실행하지 말아야 한다2")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void studyAssumeTest3() {
        Study study = new Study(100);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    @Test
    void create1() {
        System.out.println("create1");
    }

    // 모든 Test 들이 실행되기전 1번만 호출된다. (반드시 static 으로 선언해야 한다. return 타입없고, private 안된다.)
    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll");
    }

    // Test 가 실행되기 이전과 이후에 호출된다. 굳이 static 일 필요는 없다.
    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }


    @AfterEach
    void afterEach() {
        System.out.println("afterEach");
    }
}