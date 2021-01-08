package demo;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
public class Test {


    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {


        //要代理的真实对象
        Work people = new Teacher();

        //代理对象的调用处理程序，我们将要代理的真实对象传入代理对象的调用处理的构造函数中，最终代理对象的调用处理程序会调用真实对象的方法
        InvocationHandler handler = new WorkHandler(people);
        /**
         * 通过Proxy类的newProxyInstance方法创建代理对象，我们来看下方法中的参数
         * 第一个参数：people.getClass().getClassLoader()，使用handler对象的classloader对象来加载我们的代理对象
         * 第二个参数：people.getClass().getInterfaces()，这里为代理类提供的接口是真实对象实现的接口，这样代理对象就能像真实对象一样调用接口中的所有方法
         * 第三个参数：handler，我们将代理对象关联到上面的InvocationHandler对象上
         */
        Work proxy = (Work)Proxy.newProxyInstance(handler.getClass().getClassLoader(),
            people.getClass().getInterfaces(), handler);
        System.out.println(proxy.work());


        //获取默认类池，只有在这个ClassPool里面已经加载的类，才能使用
        ClassPool pool = ClassPool.getDefault();
//获取pool中的某个类
        CtClass cc = pool.get("test.Teacher");
//为cc类设置父类
        cc.setSuperclass(pool.get("test.People"));
//将动态生成类的class文件存储到path路径下
        cc.writeFile("demo");
//获取类的字节码
        byte[] b=cc.toBytecode();
//创造Point类
        CtClass point = pool.makeClass("Point");
////为cc类添加成员变量
//        point.addField(f);
////为cc类添加方法
//        point.addMethod(m);
////为cc类设置类名
//        point.setName("Pair");
    }
}
