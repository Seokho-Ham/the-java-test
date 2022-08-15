package com.whiteship.inflearnthejavatest;

import org.junit.Before;
import org.junit.Test;

public class JUnit4Test {

    @Before
    public void setup() {
        System.out.println("before");
    }

    @Test
    public void unit4test() {
        System.out.println("JUnit4Test.unit4test");
    }

}
