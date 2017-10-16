package com.squareup.lib.annotation;

/**
 * Created by liangzhenxiong on 2017/10/15.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这些注解不能被混淆
 *
 * @author lzx
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface KeepNotProguard {
}
