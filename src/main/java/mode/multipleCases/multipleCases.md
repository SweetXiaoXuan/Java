####有状态的和没有状态的多例类
如同单例类可以分成有状态的和没有状态的两种一样，多例类也可以分成有状态的和没有状态的两种。
多例对象的状态如果是可以在加载后改变的，那么这种多例对象叫做可变多例对象(MutableSingleton)；如果多例对象的状态在加载后不可以改变，那么这种多例对象叫做不变多例对象(ImmutableSingleton)。显然不变多例类的情形较为简单，而可变单例类的情形较为复杂。
如果一个系统是建立在诸如EJB和RMI等分散技术之上的，那么多例类有可能会出现数个实例，因此，在这种情况下除非提供有效的协调机制，不然最好不要使用有状态的和可变的单例类，以避免出现状态不自恰的情况。

####多例模式的应用
多例模式往往持有一个内蕴状态，多例类的每一个实例都有独特的内蕴状态。一个多例类持有一个聚集对象，用来登记自身的实例，而其内蕴状态往往就是登记的键值。当客户端通过多例类的静态工厂方法请求多例类的实例时，这个工厂方法都会在聚集内查询是否已经有一个这样的实例。如果有，就直接返还给客户端；如果没有，就首先创建一个这样的实例，将之登记到聚集中，然后再向客户端提供。

##
如果一个单例模式是一个聚集对象的话，那么这个聚集中所保存的是对其他对象的引用。一个多例模式则不同，多例对象使用一个聚集对象登记和保存自身的实例。由于这两种设计模式的相似之处，在很多情况下它们可以互换使用。