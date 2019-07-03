package com.neroarc.blog.myblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: fjx
 * @date: 2019/4/15 10:33
 * Descripe:
 */
public class HtmlUtil {
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<(?!pre|/pre)[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
    private static final String regEx_summary = "<[^>]+>";
    private static final String regEx_pre = "(?<=<pre>).+?(?=</pre>)";


    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }

    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        //htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);
        return htmlStr;
    }

    public static String getSummary(String preStr){
        Pattern p_pre = Pattern.compile(regEx_pre,Pattern.CASE_INSENSITIVE);
        Matcher m_pre = p_pre.matcher(preStr);
        preStr = m_pre.replaceAll("");

        Pattern p_summary = Pattern.compile(regEx_summary,Pattern.CASE_INSENSITIVE);
        Matcher m_summary = p_summary.matcher(preStr);
        preStr = m_summary.replaceAll("");

        return preStr;
    }



    public static void main(String[] args) {
        String str = "\n" +
                "<h4 id=\"h4-java-\"><a name=\"Java设计模式\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Java设计模式</h4><h4 id=\"h4-u88C5u9970u8005u6A21u5F0F\"><a name=\"装饰者模式\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>装饰者模式</h4><h6 id=\"h6-component\"><a name=\"Component\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Component</h6><pre><code class=\"lang-java\">public interface Component {\n" +
                "    void method();\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-concrete\"><a name=\"Concrete\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Concrete</h6><pre><code class=\"lang-java\">public class ConcreteComponent implements Component {\n" +
                "    @Override\n" +
                "    public void method() {\n" +
                "        System.out.println(\"ConcreteComponent方法\");\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-decorator\"><a name=\"Decorator\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Decorator</h6><pre><code class=\"lang-java\">public class Decorator implements Component{\n" +
                "\n" +
                "    private Component component;\n" +
                "\n" +
                "    public Decorator(Component component){\n" +
                "        this.component = component;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void method() {\n" +
                "        component.method();\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-decoratora\"><a name=\"DecoratorA\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>DecoratorA</h6><pre><code class=\"lang-java\">public class DecoratorA extends Decorator {\n" +
                "\n" +
                "    public DecoratorA(Component component){\n" +
                "        super(component);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void method() {\n" +
                "        super.method();\n" +
                "        methodA();\n" +
                "    }\n" +
                "\n" +
                "    public void methodA(){\n" +
                "        System.out.println(\"DecoratorA方法\");\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-decoratorb\"><a name=\"DecoratorB\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>DecoratorB</h6><pre><code class=\"lang-java\">public class DecoratorB extends Decorator {\n" +
                "\n" +
                "    public DecoratorB(Component component){\n" +
                "        super(component);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void method() {\n" +
                "        super.method();\n" +
                "        methodB();\n" +
                "    }\n" +
                "\n" +
                "    public void methodB(){\n" +
                "        System.out.println(\"DecoratorB方法\");\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-u6D4Bu8BD5u7C7B\"><a name=\"测试类\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>测试类</h6><pre><code class=\"lang-java\">public class Test {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        Component component = new DecoratorB(new DecoratorA(new ConcreteComponent()));\n" +
                "\n" +
                "        component.method();\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>B调用A的method，A调用ConceteCompant的method，链式。</p>\n" +
                "<p>装饰对象和真实对象有相同的接口。这样客户端对象就可以以和真实对象相同的方式和装饰对象交互</p>\n" +
                "<p>装饰对象接收所有来自客户端的请求，把这些请求转发给真实对象。</p>\n" +
                "<p>装饰对象可以在转发这些请求前或者后增加一些附加功能。</p>\n" +
                "<h4 id=\"h4-u5355u4F8Bu8BBEu8BA1u6A21u5F0F\"><a name=\"单例设计模式\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>单例设计模式</h4><h5 id=\"h5--\"><a name=\"懒汉模式(调用时才创建实例)：\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>懒汉模式(调用时才创建实例)：</h5><p>原型：</p>\n" +
                "<pre><code class=\"lang-java\">public class LanHanMode {\n" +
                "\n" +
                "    private static LanHanMode lanHanMode= null;\n" +
                "    private LanHanMode(){}\n" +
                "\n" +
                "    public static LanHanMode getInstance(){\n" +
                "        if(lanHanMode==null){\n" +
                "            lanHanMode = new LanHanMode();//懒汉模式，线程不安全\n" +
                "            //比如在多线程下，线程A进入判断条件后，cpu转到线程B，线程B判断lanHanMode为空，创建。然后又转到线程A，线程A又再次创建，存在多个实例\n" +
                "        }\n" +
                "\n" +
                "        return lanHanMode;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>在getInstance方法上加上同步：</p>\n" +
                "<pre><code class=\"lang-java\">public class LanHanMode1 {\n" +
                "\n" +
                "    private static LanHanMode1 lanHanMode1= null;\n" +
                "    private LanHanMode1(){}\n" +
                "\n" +
                "    //加锁防止多线程带来的不安全\n" +
                "    public static synchronized LanHanMode1 getInstance(){\n" +
                "        if(lanHanMode1==null){\n" +
                "            lanHanMode1 = new LanHanMode1();\n" +
                "        }\n" +
                "\n" +
                "        return lanHanMode1;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>双重检查锁定：</p>\n" +
                "<pre><code class=\"lang-java\">public class LanHanMode2 {\n" +
                "\n" +
                "    private LanHanMode2(){}\n" +
                "\n" +
                "    private static LanHanMode2 lanHanMode2 = null;\n" +
                "\n" +
                "    public LanHanMode2 getInstance(){\n" +
                "       if(lanHanMode2==null){\n" +
                "           synchronized (LanHanMode2.class){\n" +
                "               if(lanHanMode2==null){\n" +
                "                   lanHanMode2 = new LanHanMode2();\n" +
                "               }\n" +
                "           }\n" +
                "       }\n" +
                "\n" +
                "       return lanHanMode2;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>不用每次都锁定，提高效率。只有进入第一个条件的能够锁住</p>\n" +
                "<p>静态内部类：</p>\n" +
                "<pre><code class=\"lang-java\">/**\n" +
                " * @author: fjx\n" +
                " * @date: 2019/4/8 19:44\n" +
                " * Descripe:既实现了线程安全，又避免了同步带来的影响。静态方法不会加载时就\n" +
                " * 静态内部类\n" +
                " */\n" +
                "public class LanHanMode3 {\n" +
                "\n" +
                "    private static class LazyHolder{\n" +
                "        private static final LanHanMode3 INSTANCE = new LanHanMode3();\n" +
                "    }\n" +
                "\n" +
                "    private LanHanMode3(){\n" +
                "        System.out.println(\"创建\");\n" +
                "    }\n" +
                "\n" +
                "    public static final LanHanMode3 getInstance(){\n" +
                "        return LazyHolder.INSTANCE;\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"aa\");\n" +
                "        LanHanMode3.getInstance();\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h5 id=\"h5--\"><a name=\"饿汉模式(初始化时就加载)\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>饿汉模式(初始化时就加载)</h5><pre><code class=\"lang-java\">public class EHanMode {\n" +
                "\n" +
                "    private EHanMode(){}\n" +
                "\n" +
                "    private static EHanMode eHanMode = new EHanMode();\n" +
                "\n" +
                "    public static EHanMode getInstance(){\n" +
                "        return eHanMode;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h4 id=\"h4-u89C2u5BDFu8005u6A21u5F0F\"><a name=\"观察者模式\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>观察者模式</h4><p>定义了对象之间的一对多的依赖，这样一来，当一个对象改变时，它的所有的依赖者都会收到通知并自动更新。实例：一对多的推送消息。</p>\n" +
                "<p>主题接口：</p>\n" +
                "<pre><code class=\"lang-java\">public interface Subject {\n" +
                "\n" +
                "    void registerObserver(Observer observer);\n" +
                "\n" +
                "    void removeObserver(Observer observer);\n" +
                "\n" +
                "    void notifyObservers();\n" +
                "\n" +
                "\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>主题实现：</p>\n" +
                "<pre><code class=\"lang-java\">/**\n" +
                " * @author: fjx\n" +
                " * @date: 2019/4/9 13:03\n" +
                " * Descripe:\n" +
                " */\n" +
                "public class ObjectForJava implements Subject {\n" +
                "\n" +
                "    private List&lt;Observer&gt; observers = new ArrayList&lt;&gt;();\n" +
                "\n" +
                "    private String msg;\n" +
                "\n" +
                "    public void setMsg(String msg) {\n" +
                "        //当更新消息时，通知所有观察者\n" +
                "        this.msg = msg;\n" +
                "        notifyObservers();\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void registerObserver(Observer observer) {\n" +
                "        observers.add(observer);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void removeObserver(Observer observer) {\n" +
                "        if(observers.size()!=0){\n" +
                "            observers.remove(observer);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void notifyObservers() {\n" +
                "        observers.forEach((observer -&gt; observer.update(msg)));\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>观察者接口：</p>\n" +
                "<pre><code class=\"lang-java\">public interface Observer {\n" +
                "\n" +
                "    void update(String msg);\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>观察者1：</p>\n" +
                "<pre><code class=\"lang-java\">public class Observer1 implements Observer {\n" +
                "\n" +
                "    private Subject subject;\n" +
                "\n" +
                "    public Observer1(Subject subject){\n" +
                "        this.subject = subject;\n" +
                "        subject.registerObserver(this);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void update(String msg) {\n" +
                "        System.out.println(\"observer1得到最新的消息\"+msg);\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>观察者2：</p>\n" +
                "<pre><code class=\"lang-java\">public class Observer2 implements Observer {\n" +
                "\n" +
                "    private Subject subject;\n" +
                "\n" +
                "    public Observer2(Subject subject){\n" +
                "        this.subject = subject;\n" +
                "        subject.registerObserver(this);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void update(String msg) {\n" +
                "        System.out.println(\"observer2得到最新的消息\"+msg);\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>测试类：</p>\n" +
                "<pre><code class=\"lang-java\">public class Test {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        ObjectForJava objectForJava = new ObjectForJava();\n" +
                "\n" +
                "        Observer1 observer1 = new Observer1(objectForJava);\n" +
                "        Observer2 observer2 = new Observer2(objectForJava);\n" +
                "\n" +
                "        objectForJava.setMsg(\"1号信息\");\n" +
                "        objectForJava.setMsg(\"2号信息\");\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h4 id=\"h4-u5DE5u5382u6A21u5F0F\"><a name=\"工厂模式\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>工厂模式</h4><h5 id=\"h5-u7B80u5355u5DE5u5382u6A21u5F0F\"><a name=\"简单工厂模式\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>简单工厂模式</h5><h6 id=\"h6-bean\"><a name=\"bean\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>bean</h6><p>抽象类</p>\n" +
                "<pre><code class=\"lang-java\">public abstract class Car {\n" +
                "    protected String name;\n" +
                "\n" +
                "    public abstract void drive();\n" +
                "\n" +
                "    public String getName(){\n" +
                "        return this.name;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>抽象类子类1</p>\n" +
                "<pre><code class=\"lang-java\">public class BenzCar extends Car {\n" +
                "\n" +
                "    public BenzCar(){\n" +
                "        this.name=\"Benz\";\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void drive() {\n" +
                "        System.out.println(this.name+\"drive\");\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>抽象类子类2</p>\n" +
                "<pre><code class=\"lang-java\">public class AudiCar extends Car {\n" +
                "\n" +
                "    public AudiCar(){\n" +
                "        this.name=\"Audi\";\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void drive() {\n" +
                "        System.out.println(this.name+\"drive\");\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-factory\"><a name=\"factory\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>factory</h6><pre><code class=\"lang-java\">public class CarFactory {\n" +
                "\n" +
                "    public static Car createCar(String type){\n" +
                "        if(type.equals(\"audi\")){\n" +
                "            return new AudiCar();\n" +
                "        }\n" +
                "\n" +
                "        else if(type.equals(\"benz\")){\n" +
                "            return new BenzCar();\n" +
                "        }\n" +
                "\n" +
                "        else {\n" +
                "            return new AudiCar();\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-client\"><a name=\"client\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>client</h6><pre><code class=\"lang-java\">public class Driver {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        Car car = CarFactory.createCar(\"benz\");\n" +
                "        car.drive();\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h5 id=\"h5-u5DE5u5382u65B9u6CD5u6A21u5F0F\"><a name=\"工厂方法模式\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>工厂方法模式</h5><p><strong>定义：</strong>定义一个创建对象的接口，但由子类决定要实例化的类是哪一个。工厂方法模式把类实例化的过程推迟到子类。</p>\n" +
                "<h6 id=\"h6-bean\"><a name=\"bean\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>bean</h6><pre><code class=\"lang-java\">public class User {\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-dao\"><a name=\"dao\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>dao</h6><p>抽象接口</p>\n" +
                "<pre><code class=\"lang-java\">public interface IUserDao {\n" +
                "\n" +
                "    void addUser(User user);\n" +
                "\n" +
                "    void removeUser(User user);\n" +
                "\n" +
                "    User getUser(String userName);\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体1</p>\n" +
                "<pre><code class=\"lang-java\">public class MysqlUserDao implements IUserDao {\n" +
                "    @Override\n" +
                "    public void addUser(User user) {\n" +
                "        System.out.println(\"mysql user添加\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void removeUser(User user) {\n" +
                "        System.out.println(\"mysql user移除\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public User getUser(String userName) {\n" +
                "        System.out.println(\"mysql user得到\");\n" +
                "        return null;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体2</p>\n" +
                "<pre><code class=\"lang-java\">public class OracleUserDao implements IUserDao {\n" +
                "    @Override\n" +
                "    public void addUser(User user) {\n" +
                "        System.out.println(\"oracle user添加\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void removeUser(User user) {\n" +
                "        System.out.println(\"oracle user移除\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public User getUser(String userName) {\n" +
                "        System.out.println(\"oracle user得到\");\n" +
                "        return null;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-factory\"><a name=\"factory\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>factory</h6><p>接口</p>\n" +
                "<pre><code class=\"lang-java\">public interface IDaoFactory {\n" +
                "\n" +
                "    IUserDao createUserDao();\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体工厂1</p>\n" +
                "<pre><code class=\"lang-java\">public class MysqlDaoFactory implements IDaoFactory {\n" +
                "\n" +
                "    @Override\n" +
                "    public IUserDao createUserDao() {\n" +
                "        return new MysqlUserDao();\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体工厂2</p>\n" +
                "<pre><code class=\"lang-java\">public class OracleDaoFactory implements IDaoFactory {\n" +
                "    @Override\n" +
                "    public IUserDao createUserDao() {\n" +
                "        return new OracleUserDao();\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-client\"><a name=\"client\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>client</h6><pre><code class=\"lang-java\">public class Client {\n" +
                "    public static void main(String[] args) {\n" +
                "        IDaoFactory factory = new MysqlDaoFactory();\n" +
                "        IUserDao userDao = factory.createUserDao();\n" +
                "\n" +
                "        userDao.addUser(new User());\n" +
                "        userDao.removeUser(new User());\n" +
                "        userDao.getUser(\"测试\");\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>与抽象工厂模式区别在于，工厂类只提供一个创建某个对象的方法</p>\n" +
                "<h5 id=\"h5-u62BDu8C61u5DE5u5382u6A21u5F0F\"><a name=\"抽象工厂模式\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>抽象工厂模式</h5><p><strong>定义：</strong>提供一个接口，用于创建相关的或依赖对象的家族，而不需要明确指定具体类。</p>\n" +
                "<h6 id=\"h6-bean\"><a name=\"bean\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>bean</h6><pre><code class=\"lang-java\">public class Product {\n" +
                "}\n" +
                "</code></pre>\n" +
                "<pre><code class=\"lang-java\">public class Role {\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-productdao\"><a name=\"productDao\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>productDao</h6><p>抽象接口</p>\n" +
                "<pre><code class=\"lang-java\">public interface IProductDao {\n" +
                "    void addProduct(Product product);\n" +
                "\n" +
                "    void removeProduct(Product product);\n" +
                "\n" +
                "    Product getProduct(String productName);\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体1</p>\n" +
                "<pre><code class=\"lang-java\">public class MysqlProductDao implements IProductDao {\n" +
                "\n" +
                "    @Override\n" +
                "    public void addProduct(Product product) {\n" +
                "        System.out.println(\"mysql product增加\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void removeProduct(Product product) {\n" +
                "        System.out.println(\"mysql product移除\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public Product getProduct(String productName) {\n" +
                "        System.out.println(\"mysql product得到\");\n" +
                "        return null;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体2</p>\n" +
                "<pre><code class=\"lang-java\">public class OracleProductDao implements IProductDao{\n" +
                "\n" +
                "    @Override\n" +
                "    public void addProduct(Product product) {\n" +
                "        System.out.println(\"oracle product增加\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void removeProduct(Product product) {\n" +
                "        System.out.println(\"oracle product移除\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public Product getProduct(String productName) {\n" +
                "        System.out.println(\"oracle product得到\");\n" +
                "        return null;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-roledao\"><a name=\"roleDao\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>roleDao</h6><p>抽象接口</p>\n" +
                "<pre><code class=\"lang-java\">public interface IRoleDao {\n" +
                "\n" +
                "    void addRole(Role role);\n" +
                "\n" +
                "    void removeRole(Role role);\n" +
                "\n" +
                "    Role getRole(String roleName);\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体1</p>\n" +
                "<pre><code class=\"lang-java\">public class MysqlRoleDao implements IRoleDao {\n" +
                "\n" +
                "\n" +
                "    @Override\n" +
                "    public void addRole(Role role) {\n" +
                "        System.out.println(\"mysql role增加\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void removeRole(Role role) {\n" +
                "        System.out.println(\"mysql role移除\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public Role getRole(String roleName) {\n" +
                "        System.out.println(\"mysql role得到\");\n" +
                "        return null;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体2</p>\n" +
                "<pre><code class=\"lang-java\">public class OracleRoleDao implements IRoleDao {\n" +
                "\n" +
                "    @Override\n" +
                "    public void addRole(Role role) {\n" +
                "        System.out.println(\"oracle role增加\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void removeRole(Role role) {\n" +
                "        System.out.println(\"oracle role移除\");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public Role getRole(String roleName) {\n" +
                "        System.out.println(\"oracle role得到\");\n" +
                "        return null;\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-factory\"><a name=\"factory\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>factory</h6><p>抽象接口</p>\n" +
                "<pre><code class=\"lang-java\">public interface IDaoFactory {\n" +
                "\n" +
                "    IRoleDao createRoleDao();\n" +
                "\n" +
                "    IProductDao createProductDao();\n" +
                "\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体1</p>\n" +
                "<pre><code class=\"lang-java\">public class MysqlDaoFactory implements IDaoFactory {\n" +
                "\n" +
                "    @Override\n" +
                "    public IRoleDao createRoleDao() {\n" +
                "        return new MysqlRoleDao();\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public IProductDao createProductDao() {\n" +
                "        return new MysqlProductDao();\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<p>具体2</p>\n" +
                "<pre><code class=\"lang-java\">public class OracleDaoFactory implements IDaoFactory {\n" +
                "\n" +
                "    @Override\n" +
                "    public IRoleDao createRoleDao() {\n" +
                "        return new OracleRoleDao();\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public IProductDao createProductDao() {\n" +
                "        return new OracleProductDao();\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "<h6 id=\"h6-client\"><a name=\"client\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>client</h6><pre><code class=\"lang-java\">public class Client {\n" +
                "    public static void main(String[] args) {\n" +
                "        IDaoFactory factory = new MysqlDaoFactory();\n" +
                "        IRoleDao roleDao = factory.createRoleDao();\n" +
                "\n" +
                "        roleDao.addRole(new Role());\n" +
                "        roleDao.removeRole(new Role());\n" +
                "        roleDao.getRole(\"测试\");\n" +
                "\n" +
                "        IProductDao productDao = factory.createProductDao();\n" +
                "        productDao.addProduct(new Product());\n" +
                "        productDao.removeProduct(new Product());\n" +
                "        productDao.getProduct(\"测试\");\n" +
                "    }\n" +
                "}\n" +
                "</code></pre>\n" +
                "edit.html:324:21\n" +
                "\n" +
                "\u200B\n" +
                "\n";

        String temp = "<h3 id=\"h3-u6D4Bu8BD5u7B2Cu4E09u6B21\"><a name=\"测试第三次\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>测试第三次</h3>";
        String preStr = getTextFromHtml(temp);
        System.out.println(preStr);
        System.out.println(getSummary(preStr));
    }

}

