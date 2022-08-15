package com.whiteship.inflearnthejavatest;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

public class FindSlowTestExtension implements BeforeTestExecutionCallback,
    AfterTestExecutionCallback {

    private final long threshold;

    public FindSlowTestExtension(long threshold) {
        this.threshold = threshold;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String testMethodName = context.getRequiredTestMethod().getName();
        SlowTest annotation = context.getRequiredTestMethod().getAnnotation(SlowTest.class);

        if (annotation != null) {
            return;
        }

        Store store = getStore(context);

        Long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;
        if (duration > threshold) {

            System.out.printf("Please consider mark method [%s] with @SlowTest.\n", testMethodName);
        }
    }

    private static Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        return context.getStore(Namespace.create(testClassName, testMethodName));
    }
}
