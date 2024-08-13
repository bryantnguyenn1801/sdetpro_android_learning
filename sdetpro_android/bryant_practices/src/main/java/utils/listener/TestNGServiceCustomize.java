package utils.listener;

import com.epam.reportportal.listeners.ItemStatus;
import com.epam.reportportal.testng.TestMethodType;
import com.epam.reportportal.testng.TestNGService;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.attribute.ItemAttributesRQ;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.ITestResult;
import org.testng.internal.TestResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestNGServiceCustomize extends TestNGService {

    public TestNGServiceCustomize() {
        super();
    }

    @Override
    @Nonnull
    protected StartTestItemRQ buildStartConfigurationRq(@Nonnull final ITestResult testResult, @Nullable final TestMethodType type) {
        StartTestItemRQ rq = super.buildStartConfigurationRq(testResult, type);
        String testName = "";

        if (type == TestMethodType.BEFORE_METHOD || type == TestMethodType.AFTER_METHOD) {
            if (testResult.getParameters().length > 0) {
                Object firstParam = testResult.getParameters()[0];
                if (!firstParam.equals("chrome") && !firstParam.equals("safari") && !firstParam.equals("hchrome")) {
                    TestResult result = (TestResult) firstParam;
                    testName = result.getMethod().getMethodName();
                }
            } else {
                testName = testResult.getInstanceName();
            }
            Set<ItemAttributesRQ> itemAttributesRQSet = new HashSet<>();
            itemAttributesRQSet.add(new ItemAttributesRQ("configFor", testName));
            rq.setAttributes(itemAttributesRQSet);
        }
        return rq;
    }

    @Override
    protected FinishTestItemRQ buildFinishTestMethodRq(ItemStatus status, ITestResult testResult) {
        FinishTestItemRQ rq = super.buildFinishTestMethodRq(status, testResult);
        TestMethodType type = getAttribute(testResult, RP_METHOD_TYPE);
        if (type == TestMethodType.STEP) {
            Set<ItemAttributesRQ> itemAttributesRQSet = new HashSet<>();
            String epicValue = getClassAnnotation(testResult, Epic.class);
            String featureValue = getClassAnnotation(testResult, Feature.class);
            itemAttributesRQSet.add(new ItemAttributesRQ("epic", epicValue));
            itemAttributesRQSet.add(new ItemAttributesRQ("feature", featureValue));
            itemAttributesRQSet.add(new ItemAttributesRQ("classPath", testResult.getTestClass().getName()));
            itemAttributesRQSet.add(new ItemAttributesRQ("debug", status.name()));
            rq.setAttributes(itemAttributesRQSet);
        }
        if (status == ItemStatus.FAILED) {
            rq.setDescription(testResult.getThrowable().getMessage());
        }
        return rq;
    }

    private <T> String getClassAnnotation(ITestResult result, Class<T> clazz) {
        try {
            Class c = result.getTestClass().getRealClass();
            if (c.isAnnotationPresent(clazz)) {
                Class<? extends Annotation> a = c.getAnnotation(clazz).annotationType();
                Method m = clazz.getDeclaredMethod("value");
                return m.invoke(c.getAnnotation(clazz)).toString();
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result.getTestClass().getName();
    }
}
