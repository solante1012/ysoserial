package annotationDemo;

@HelloAnnotation(say = "Do it!")  //memberValues {say: "Do it!"}
public class TestMain {
    public static void main(String[] args) {


        HelloAnnotation annotation = TestMain.class.getAnnotation(HelloAnnotation.class);//获取TestMain类上的注解对象
/*      可以看到HelloAnnotation注解的实例是jvm生成的动态代理类的对象。

        这个运行时生成的动态代理对象是可以导出到文件的，方法有两种
        在代码中加入System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        在运行时加入jvm 参数 -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true*/

        System.out.println(annotation.say());//调用注解对象的say方法，并打印到控制台

/*      注解本质是一个继承了Annotation的特殊接口，其具体实现类是Java运行时生成的动态代理类。
        通过代理对象调用自定义注解（接口）的方法，
        会最终调用AnnotationInvocationHandler的invoke方法。
        该方法会从memberValues这个Map中索引出对应的值。而memberValues的来源是Java常量池。*/


        /*
        而Annotation接口声明了以下方法。

        public interface Annotation {
            boolean equals(Object var1);

            int hashCode();

            String toString();

            Class<? extends Annotation> annotationType();
        }

        这些方法，已经被$Proxy1实现了。（这就是动态代理的机制）
        动态代理方法的调用最终会传递给绑定的InvocationHandler实例(AnnotationInvocationHandler)的invoke方法处理
*/
    }
}
