package com.example.javatestdriven;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS)
public class TestInstanceTest {

    int value = 1;

    @FastTest
    @DisplayName("스터디 만들기 fast")
    void create_new_study() {
        System.out.println("this = " + this);
        System.out.println("value++ = " + value++);
//        Study actual = new Study(1);
//        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @SlowTest
    @DisplayName("스터디 만들기 slow")
    void 테스트명() {
        System.out.println("value = " + value++);
    }

}
