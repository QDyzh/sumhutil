package top.sumhzehn.test.build;

/**
 * @author SumhZehn
 * 2021/11/10 16:24
 *  抽象建造者，定义每个部件
 **/
public interface Builder {
    void buildPartA(Product p);
    void buildPartB(Product p);
    void buildPartC(Product p);
}
