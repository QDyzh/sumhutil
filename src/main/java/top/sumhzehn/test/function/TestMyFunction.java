package top.sumhzehn.test.function;

/**
 * JDK8 测试函数式接口调用
 * @author Administrator
 *
 */
public class TestMyFunction {

	public TestMyFunction(String message, MyFunction function) {
		function.sout(message);
	}
	
	public static void main(String[] args) {
		String a = "AAAA";
		new TestMyFunction("1231232", (message) -> { System.out.println(message + a); });
	}
}
