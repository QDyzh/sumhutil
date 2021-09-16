package top.sumhzehn.annotation.excel;

import java.lang.annotation.*;

/**
 * SumhZehn
 * 2021/9/16 20:44
 * Excel文档title注解
 **/

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTitle {
    String value();
}
