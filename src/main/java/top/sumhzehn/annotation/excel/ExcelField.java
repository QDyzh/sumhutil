package top.sumhzehn.annotation.excel;

import java.lang.annotation.*;

/**
 * @author SumhZehn
 * 2021/12/20 16:53
 **/
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
    String value();
}
