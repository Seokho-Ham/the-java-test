package com.whiteship.inflearnthejavatest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ExtendWith(FindSlowTestExtension.class)
class StudyTest {

    @RegisterExtension
    static final FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(2000L);

    @Test
    @Order(1)
    void test1() {
        Study study = new Study(10);

        assertAll(
            () -> assertNotNull(study),
            () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                () -> "Status가" + StudyStatus.DRAFT + "이어야 합니다."),
            () -> assertTrue(study.getLimit() < 11, () -> "11 이상이어야 합니다.")
        );

    }

    @Test
    @Order(2)
    void test2() throws InterruptedException {
        String env = System.getenv("TEST_ENV");
        Thread.sleep(3000);
        assumeTrue("LOCAL".equalsIgnoreCase(env));

        assumingThat("LOCAL".equalsIgnoreCase(env), () -> {
            System.out.println(env);
            Study study = new Study(10);
            assertTrue(study.getLimit() < 11);
        });

        Study study = new Study(10);
        assertTrue(study.getLimit() < 11);
    }

    @FastTest
    void fast_test() {
        Study study = new Study(10);
        assertTrue(study.getLimit() < 11);
    }

    @SlowTest
    void slow_test() throws InterruptedException {
        Study study = new Study(10);
        Thread.sleep(3000);
        assertTrue(study.getLimit() < 11);
    }

    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetition}")
    void repeatedTest(RepetitionInfo repetitionInfo) {
        System.out.println("test" + repetitionInfo.getCurrentRepetition());
    }

    @DisplayName("반복 테스트 2")
    @ParameterizedTest(name = "{index} / {displayName} / message : {0}")
    @ValueSource(strings = {"안녕하세요", "테스트", "중입니다"})
    void parameterizedTest(String message) {
        System.out.println("message = " + message);
    }
}