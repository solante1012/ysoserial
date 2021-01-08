# Java反序列化之Commons-Collections

https://badcode.cc/2018/03/15/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B9%8BCommons-Collections/

寻找会自动调用`InvokerTransformer`类中的`transform()`方法，构造代码执行。明显调用`transform()`方法有以下两个类：

- TransformedMap
- LazyMap



![image-20210108205143038](https://gitee.com/solante_admin/pic-goimage/raw/master/img/20210108205150.png)



每次循环调用的object 刚好为返回值 继续调用

![image-20210108210140669](https://gitee.com/solante_admin/pic-goimage/raw/master/img/20210108210140.png)