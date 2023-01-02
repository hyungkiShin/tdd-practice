package com.example.javatestdriven;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class StudyTest {

    @Test
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
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