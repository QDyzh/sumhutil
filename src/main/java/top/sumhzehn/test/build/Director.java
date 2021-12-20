package top.sumhzehn.test.build;

/**
 * @author SumhZehn
 * 2021/11/10 17:01
 *  指挥者 针对抽象产品类，定义生产过程
 **/
public class Director {

    private Builder builder;

    public Director setBuilder(Builder builder) {
        this.builder = builder;
        return this;
    }

    public Product construct() {
        Product p = new Product();
        builder.buildPartA(p);
        builder.buildPartB(p);
        builder.buildPartC(p);
        return p;
    }
}
