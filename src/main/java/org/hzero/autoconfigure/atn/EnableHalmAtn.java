package org.hzero.autoconfigure.atn;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AtnAutoConfiguration.class)
public @interface EnableHalmAtn {

}
