package org.hzero.halm.afm.infra.annotation;

import joptsimple.internal.Strings;

import java.lang.annotation.*;

/**
 * 记录一些字段的信息
 * @author WangSen 2019/4/10 0010 18:57
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldMessage {

    String fieldName() default Strings.EMPTY;
    String tag() default Strings.EMPTY;
    String des() default Strings.EMPTY;
}
