package com.andyadc.demo;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Reference
 *
 * @author andy.an
 * @since 2017/11/2
 */
public class ReferenceDemo {

    public static void main(String[] args) {
        // 强引用
        // 只要强引用还存在，垃圾收集器永远不会回收掉被引用的对象
        Object o = new Object();
        System.out.println(o);

        System.out.println("********************");

        // 软引用
        // 软引用是用来描述一些还有用但并非必需的对象。对于软引用关联着的对象，在系统将要发生内存溢出异常之前，将会把这些对象列进回收范围之中进行第二次回收。
        SoftReference<String> stringSoftReference = new SoftReference<String>("SoftReference");
        System.out.println(stringSoftReference.get());
        System.gc();
        System.out.println(stringSoftReference.get());
        System.out.println(stringSoftReference.isEnqueued());

        System.out.println("********************");

        // 弱引用
        // 弱引用也是用来描述非必需对象的，但是它的强度比软引用更弱一些，被弱引用关联的对象只能生存到下一次垃圾收集发生之前。当垃圾收集器工作时，无论当前内存是否足够，都会回收掉只被弱引用关联的对象。
        WeakReference<String> stringWeakReference = new WeakReference<String>("WeakReference");
        System.out.println(stringWeakReference.get());
        System.gc();
        System.out.println(stringWeakReference.get());
        System.out.println(stringWeakReference.isEnqueued());

        System.out.println("********************");

        // 虚引用
        // 虚引用也称为幽灵引用或者幻影引用，它是最弱的一种引用关系。一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，也无法通过虚引用来取得一个对象实例。为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知。
        ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        PhantomReference<String> stringPhantomReference = new PhantomReference<String>("PhantomReference", referenceQueue);
        System.out.println(stringPhantomReference.get());
        System.out.println(stringPhantomReference.isEnqueued());

    }
}
