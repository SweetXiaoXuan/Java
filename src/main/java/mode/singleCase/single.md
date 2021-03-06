显然单例模式的要点有三个：一是某个类只能有一个实例；二是它必须自行创建这个
事例；三是它必须自行向整个系统提供这个实例。

####使用情况
使用单例模式有一个必要条件：在一个系统要求一个类只有一个实例时才应当使用单
例模式。反过来说，如果一个类可以有几个实例共存，那么就没有必要使用单例类。


###单例类的状态
####有状态的单例类
一个单例类可以是有状态的(stateful),—个有状态的单例对象一般也是可变(mutable)单例对象。
有状态的可变的单例对象常常当做状态库(repositary)使用。比如一个单例对象可以持有一个int类型的属性，用来给一个系统提供一个数值惟一的序列号码，作为某个贩卖系统的账单号码。
当然，一个单例类可以持有一个聚集，从而允许存储多个状态。

####没有状态的单例类
另一方面，单例类也可以是没有状态的(stateless),仅用做提供工具性函数的对象。既然是为了提供工具性函数，也就没有必要创建多个实例，因此使用单例模式很合适。一个没有状态的单例类也就是不变(Immutable)单例类。

####为什么需要使用单例模式
属性是系统的一种“资源”，应当避免有多于一个的对象读取，特别是存储属性。此外，属性的读取可能会在很多地方发生，创建属性对象的地方应当在哪里不是很清楚。换言之,属性管理器应当自己创建自己的实例，并且自己向系统全程提供这一实例。
因此，属性文件管理器应当是由一个单例模式负责的。


####不完全的单例类
```ruby
public class LazySingleton {
    private static LazySingleton m_instance = null;
    
    /**
      * 公开的构造子，外界可以直接实例化
      */
    public LazySingleton() {}
    
    /**
      * 静态工厂方法
      * @return返还LazySingleton类的惟一的实例
      */
    synchronized public static LazySingleton getlnstance() {
        if(m_instance == null){
            m_instance = new LazySingleton();
        }
        return m_instance;
    }
}
```
上面的代码乍看起来是一个“懒汉”式单例类，仔细一看，发现有一个公开的构造子。由于外界可以使用构造子创建出任意多个此类的实例，这违背了单例类只能有一个(或有限个)实例的特性，因此这个类不是完全的单例类。这种情况有时会出现，比如javax.swing.TimerQueue便是一例.

造成这种情况出现的原因有以下几种可能：
(1)初学者的错误。许多初学者没有认识到单例类的构造子不能是公开的，因此犯下这个错误。有些初学Java语言的学员甚至不知道一个Java类的构造子可以不是公开的。在这种情况下，设计师可能会通过自我约束，也就是说不去调用构造子的办法，将这个不完全的单例类在使用中作为一个单例类使用。
在这种情况下，一个简单的矫正办法，就是将公开的构造子改为私有的构造子。
(2)当初出于考虑不周，将一个类设计成为单例类，后来发现此类应当有多于一个的实例。为了弥补错误，干脆将构造子改为公开的，以便在需要多于一个的实例时，可以随时调用构造子创建新的实例。
要纠正这种情况较为困难，必须根据具体情况做出改进的决定。如果一个类在最初被
设计成为单例类，但后来发现实际上此类应当有有限多个实例，这时候应当考虑是否将单
例类改为多例类（Multiton)。
(3)设计师的Java知识很好，而且也知道单例模式的正确使用方法，但是，还是有意使用这种不完全的单例模式，因为他意在使用一种“改良”的单例模式。这时候，除去共有的构造子不符合单例模式的要求之外，这个类必须是很好的单例模式。

####默认实例模式
有些设计师将这种不完全的单例模式叫做“默认实例模式”（DefaultInstancePattern)。在所谓的“默认实例模式”里面，一个类提供静态的方法，如同单例模式一样，同时又提供一个公开的构造子，如同普通的类一样。
这样做的惟一好处是，这种模式允许客户端选择如何将类实例化：创建新的自己独有的实例，或者使用共享的实例。
这样一来，由于没有任何的强制性措施，客户端的选择不一定是合理的选择。其结果是设计师往往不会花费时间在如何提供最好的选择上，而是不恰当地将这种选择交给客户端的程序员，这样必然会导致不理想的设计和欠考虑的实现。
本书建议读者不要这样做。


####单例模式的应用
上面讨论了序列的存储机制，另一个重要的机制是键的查询管理机制。与其将键值的查询工作交给各个模块，不如将之集中到•-个对象身上。这个对象负责管理序列键的查询，称之为序列键管理器。
显然，不难看出，整个系统只需要一个序列键管理器对象。由于系统运行期间总是需要序列键，因此序列键管理器对象需要在系统运行期间存在。考虑到可以让一个序列键管理器负责管理分属于不同模块的多个序列键，因此这个序列键管理器需要让整个系统访问。
学习过单例模式的读者会意识到，这个系统设计应当使用到单例模式。是的，这个序列键管理器可以设计成一个单例类。
一个客户端系统往往需要管理不止一个键值，而是多个键值。这时候，可以将这个单例对象的内部状态扩展成为一个聚集，从而可以存储任意多个键值。也就是说，这个序列键管理器是一个聚集对象，而此聚集本身是一个单例对象。