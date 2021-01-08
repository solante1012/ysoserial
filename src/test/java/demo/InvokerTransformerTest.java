package demo;

import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.IOException;

public class InvokerTransformerTest {
    public static void main(String[] args) {
        InvokerTransformer invokerTransformer = new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{new String("calc")});
        invokerTransformer.transform(Runtime.getRuntime());
//        try {
//            Runtime.getRuntime().exec("calc");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
