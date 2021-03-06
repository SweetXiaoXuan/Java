建造模式是对象的创建模式[GOF95]。建造模式可以将一个产品的内部表象与产品的生成过程分割开来，从而可以使一个建造过程生成具有不同的内部表象的产品对象。

####产品的内部表象
一个产品常有不同的组成成分作为产品的零件，这些零件有可能是对象，也有可能不
是对象，它们通常又叫做产品的内部表象(internalrepresentation)。不同的产品可以有不
同的内部表象，也就是不同的零件。使用建造模式可以使客户端不需要知道所生成的产品
对象有哪些零件，每个产品的对应零件彼此有何不同，是怎么建造出来的，以及怎样组成
产品。

####对象性质的建造
有些情况下，一个对象会有一些重要的性质，在它们没有恰当的值之前，对象不能作
为一个完整的产品使用。比如，一个电子邮件有发件人地址、收件人地址、主题、内容、
附录等部分，而在最起码的收件人地址得到赋值之前，这个电子邮件不能发出。
有些情况下，一个对象的一些性质必须按照某个顺序赋值才有意义。在某个性质没有
赋值之前，另一个性质则无法赋值。这些情况使得性质本身的建造涉及到复杂的商业逻辑。
这时候，此对象相当于一个有待建造的产品，而对象的这些性质相当于产品的零件，
建造产品的过程是建造零件的过程。由于建造零件的过程很复杂，因此，这些零件的建造
过程往往被“外部化”到另外一个称做建造者的对象里，建造者对象返还给客户巧的是一
个全部零件都建造完毕的产品对象。"
建造模式非常适用于此种情况。建造模式利用一个导演者对象和具体建造者对象一个
--个地建造出所有的零件，从而建造出完整的产品对象。建造者模式将产品的结构和产品
的零件建造过程对客户端隐藏起来，把对建造过程进行指挥的责任和具体建造者零件的责
任分割开来，达到责任划分和封装的目的。

####命名的考虑
应当指出的是，这些零件有可能是独立的对象，也有可能仅仅以对象的组成成分（如
性质）的形式存在于产品对象内部。因此，本书使用“零件”，而不使用“零件对象”、“部
件”和“构件”等往往意味着对象的词汇。对零件的产生，本书使用“建造”一词，而不
使用“创建”或者“生成”一词，因为“创建”或者“生成”往往是针对对象而言的。


###建造模式的结构
•抽象建造者(Builder)角色：给出一个抽象接口，以规范产品对象的各个组成成分的建造。一般而言，此接口独立于应用程序的商业逻辑。模式中直接创建产品对象的是具体建造者(ConcreteBuilder)角色。具体建造者类必须实现这个接口所要求的两种方法：一种是建造方法，比如本例子中的buildPartlO和buildPar2();另一种是结果返还方法，即本例子中的retrieveResult()o一般来说，产品所包含的零件数目与建造方法的数目相符。换言之，有多少零件，就有多少相应的建造方法。
•具体建造者(ConcreteBuilder)角色：担任这个角色的是与应用程序紧密相关的一些类，它们在应用程序调用下创建产品的实例。这个角色要完成的任务包括：
    ①实现抽象建造者Builder所声明的接口，给出一步一步地完成创建产品实例的操作。
    ②在建造过程完成后，提供产品的实例。
•导演者（Director)角色：担任这个角色的类调用具体建造者角色以创建产品对象。应当指出的是，导演者角色并没有产品类的具体知识，真正拥有产品类的具体知识的是具体建造者角色。
•产品（Product)角色：产品（Product)便是建造中的复杂对象。一般来说，一个系统中会有多于•个的产品类，而且这些产品类并不一定有共同的接口，而完全可以是不相关联的。

###建造模式的实现
####空的零件建造方法
阅读本章的读者，读到这里可能已经有了一些问题要问。其中一个常见的问题就是，当一个系统有多个产品类的时候，怎么能够保证它们所对应的具体建造者类都有同样的接口呢？
当然，产品的内部结构细节大多可以与具体建造者类的接口无关，因为接口仅仅给出建造方法的特征而已。但是接口确实规定了具体建造者类有几个零件建造方法，也就是说，抽象建造者角色确实规定了产品类必须有同样数目的零件，以及具体有几个零件。如果有一些产品有较多的零件，而有些产品有较少的零件，建造模式还可以使用吗？
回答是肯定的。这些产品并不一定要有同样数目的零件才可以使用建造模式。如果一个产品有较少的零件，可以使用空的零件建造方法，忽略没有的零件。
这种为不感兴趣的方法提供平庸实现的做法是缺省适配（DefaultAdapter)模式的精神。

####省略抽象建造者角色
建造模式在实现时可以根据具体情况做一些变化。
首先，如果设计师非常肯定系统只需要一个具体建造者角色的话，可以省略掉抽象建造者角色。抽象建造者角色存在的目的是规范具体建造者角色的行为，而系统如果只有一个具体建造者，那么这个规范者角色就不需要了，这时模式的类图如下图所示。

####省略导演者角色
可以看出，Builder角色自己扮演了导演者和建造者的双重角色。此时有改变的类是Builder类，
相应地，模式的时序图也有所不同，如下图所示。客户端创建一个Builder的实例，同时Builder创建一个Product的实例。客户端调用constructO方法，此方法调用buildPartl()和buildPart2()等零件建造方法，建造出完整的产品对象。最后，客户端调用retrieveProduct()方法得到最后产品的实例。


####过渡到模版方法模式
准备一个抽象类，将部分逻辑以具体方法以及具体构造子的形式实现，然后声明一些抽象方法来迫使子类实现剩余的逻辑。不同的子类可以以不同的方式实现这些抽象方法，从而对剩余的逻辑有不同的实现，这就是模版方法模式。
有意思的是，这个特殊退化的建造模式与模版方法模式有相似之处：constructO方法就相当于模版方法，这个方法调用其他的建造方法，如buildPartl()、buildPart2()等基本方法，因此，这使得此系统与模版方法模式相同。模版方法模式的简略类图如右图所示。
这就是说，如果系统的要求发生变化、要求有不同的零件生成逻辑时，那么设计师就有两种选择：一是修改这个退化的建造模式，将它改回成为完全的建造模式，这当然就要涉及到代码的修改；二是不修改已有的代码，而是将Builder类扩展到不同的子类，在这些子类里面置换掉需要改变的建造方法。
第一种选择局限于建造模式；第二种选择则引入了模版方法（TemplateMethod)模式。

####合并建造者角色和产品角色
考虑建造模式失去抽象建造者角色和导演者角色之后，还可以进一步退化、从而失去具体建造者角色的情况。此时具体建造者角色与产品角色合并，从而使得产品自己就是自己的建造者。
显然，这样做混淆了对象的创立者和对象本身。但是，有时候一个产品对象有着固定的几个零件，而且永远只有这几个零件。此时将产品类与建造类合并，可以使系统简单易读。可以看出，此时这些零件就变成了产品的性质。
读者可以在后面看到，JavaMail库包中的Message类就是一个退化的建造模式，它的性质如from、recipient、subject、text等，均可以看做是Message的“零件”。
在很多情况下，建造模式实际上是将一个对象的性质建造过程外部化到独立的建造者对象中，并通过一个导演者角色对这些外部化的性质赋值过程进行协调。作为理解建造模式的办法，这是很有启发意义的。

####建造者角色可以有多个产品构造方法
建造者角色可以同时有多于一个的产品构造方法，可以重载或者使用不同的方法名。这样可以使得产品对象的创建方式多样化

###在什么情况下使用建造模式
在以下情况下应当使用建造模式：
(1)需要生成的产品对象有复杂的内部结构。每一个内部成分本身可以是对象，也可以仅仅是一个对象（即产品对象）的一个组成成分。
(2)需要生成的产品对象的属性相互依赖。建造模式可以强制实行一种分步骤进行的建造过程，因此，如果产品对象的一个属性必须在另一个属性被赋值之后才可以被赋值，使用建造模式便是一个很好的设计思想。
比如，在美国的各个州购买商品需要缴纳购买人所在州的销售税，而各个州的销售税均不相同。因此，当消费者打电话购买商品时，一个销售系统必须知道消费者是从哪一个州付账，才可以决定销售税的比例。对应的信息系统必须要求使用者将顾客所在的州输入到系统中，才能看到销售税和总额。建造模式适合于在这种情况下使用。
有时产品对象的属性并无彼此依赖的关系，但是在产品的属性没有确定之前，产品对象不能使用。这时产品对象的实例化，属性的赋值和使用仍然是分步骤进行的。因此，建造模式仍然有意义。
(3)在对象创建过程中会使用到系统中的其他一些对象，这些对象在产品对象的创建过程中不易得到。
同时，使用建造模式主要有以下的效果：
    •建造模式的使用使得产品的内部表象可以独立地变化。使用建造模式可以使客户端不必知道产品内部组成的细节。
    •每一个Builder都相对独立，而与其他的Builder无关。
    •模式所建造的最终产品更易于控制。

###建造模式与其他模式的关系
建造模式与抽象工厂模式和策略模式等有相似之处，但又有不同的地方。

####建造模式与抽象工厂模式的区别
读者可能已经注意到了，建造模式与抽象工厂模式非常相像，而两者又都是用来创建同时属于几个产品族的对象的模式。那么这两种模式有什么样的区别呢？抽象工厂模式的简略类图如下图所示。
在抽象工厂模式中，每一次工厂对象被调用时都会返还一个完整的产品对象，而客户端有可能会决定把这些产品组装成一个更大更复杂的产品，也有可能不会。建造类则不同,它一点一点地建造出一个复杂的产品，而这个产品的组装过程就发生在建造者角色内部。建造者模式的客户端拿到的是一个完整的最后产品。
换言之，虽然抽象工厂模式与建造模式都是设计模式，但是抽象工厂模式处在更加具体的尺度上，而建造模式则处于更加宏观的尺度上。一个系统可以由一个建造模式和一个抽象工厂模式组成，客户端通过调用这个建造角色，间接地调用另一个抽象工厂模式的工厂角色。工厂模式返还不同产品族的零件，而建造者模式则把它们组装起来。
比如仍以众神造人为例，女娲利用建造模式负责把灵魂、耳目、手臂等组合成一个完整的人，而黄帝、上骈、桑林各自利用工厂模式创造出灵魂、耳目、手臂等。女娲不必考虑灵魂、耳目、手臂是什么样子、怎么创造出来的，这就成为一个由建造模式和抽象工厂模式组合而成的系统。

####建造模式与策略模式的区别
建造模式在结构上很接近于策略模式，事实上建造模式是策略模式的一种特殊情况，这两种模式的区别在于它们的用意不同。建造模式适用于为客户端一点一点地建造新的对象，而不同类型的具体建造者角色虽然都拥有相同的接口，但是它们所创建出来的对象则可能完全不同。策略模式的简略类图如下图所示。
策略模式的目的是为算法提供抽象的接口。换言之，•个具体策略类把一个算法包装到一个对象里面，而不同的具体策略对象为一种一般性的服务提供不同的实现。

####从建造模式过渡到模版方法模式
正如前面所指出的，建造模式在退化、失去导演者角色后，可以顺理成章地发展到模版方法模式。模版方法模式的简略类图如右图所示。

####使用缺省适配模式
如果某个产品没有某个零件的话，那么此产品所对应的具体建造者类便无法实现对应于那个零件的建造方法。这时便可以使用缺省适配模式，提供•个建造方法的平庸实现，也就是不产生真正的建造行为的实现。缺省适配模式的简略类图如下图所示。

####建造模式与合成模式的关系
正如本章前面所说的，产品的零件可以是对象，也可以不是对象，而是对象的某种组成成分。当产品的零件确实是对象时，产品对象就变成了复合对象，因为产品内部还含有子对象。这种对象内含有子对象的结构，可以使用合成模式描述。
换言之，合成模式描述一个对象树的组成结构，而建造模式则可以用来描述对象树的生成过程。合成模式的简略类图如下图所示