package org.nacol.cooltool.performanceanalysis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PerformanceAnalysis {

    String processName() default "";

    String taskName();

    boolean startFlag() default false;

    boolean endFlag() default false;

}
