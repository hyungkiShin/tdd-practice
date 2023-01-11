package com.example.javatestdriven;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 타겟을 (에노테이션을 어디에?) 메소드로 지정
@Retention(RetentionPolicy.RUNTIME) // 사용한 코드가 런타임까지 유지가 되어야 한다
@Tag("slow")
@Test
public @interface SlowTest {
}