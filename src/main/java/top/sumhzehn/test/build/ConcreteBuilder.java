package top.sumhzehn.test.build;

/**
 * @author SumhZehn
 * 2021/11/10 16:34
 *  具体建造者
 **/
public class ConcreteBuilder implements Builder{

    @Override
    public void buildPartA(Product p) {
        p.setA("A部件");
    }

    @Override
    public void buildPartB(Product p) {
        p.setB("B部件");
    }

    @Override
    public void buildPartC(Product p) {
        p.setC("C部件");
    }
}
