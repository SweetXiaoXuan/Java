#### @ param

@ param标签可以归档方法或构造器的某个单一参数，或者归档类、接口以及泛型方法的类型参数。在使用@ param标签时，我们应该针对方法的每一个参数都使用一个该标签。每个段落的第一个词会被当作参数名，而余下的部分则会被当作是对它的描述:

```
@param max The maximum number of words to read.
```

当归档类型参数时，我们应该在类型参数名两边加上<和>:

```
@param一e element  type of this  List 
```

然而，类型参数通常并不需要显式的文档，因为它们的意义都很明显。

#### @ see

@ see标签可以创建链接到其他javadoc文档的交叉引用。我们可以在该标签的后面命名任何标识符，尽管我们必须对它们进行充分的限定。例如，通常可以使用某个类的成员的简单名来命名它，但是如果该成员是一个重载方法，我们就必须通过列举各个参数的类型来指定该方法的重载版本。我们可以使用未限定的名字来指定当前包内的接口或类，但必须使用完全限定名来指定其他包中的类型。我们可以通过在成员名的前面使用#来指定类型的成员。下面是所有有效的@ see标签格式:

```
 @see #getName

 @see Attr

 @see com.magic.attr.Attr

 @see com.magic.attr.Deck#DECK-SIZE

 @see com.magic.attr.Attr#getName

 @see com.magic.attr.Attr#Attr(String)

 @see com.magic.attr.Attr#Attr(String，Object)

 @see com.magic.attr

 @see AttributeSpecification

 @see "The JavaDeveloper's Almanac"
```

第一种形式所指的是名为getName的方法，该方法与文档注释自身位于同一个类或接口中，或者位于某个包围类或包围接口中，这样的语法也可以应用于构造器和字段。第二种形式引用的是当前包中的类或某个导人包中的类。第三种形式使用完全限定名引用了某个类。

后4种形式的@see引用的是成员，其中，前两个是关于域(DECK-SIZ日和方法(getName)的形式。我们可以直接使用方法名，因为在Attr类中只定义了一个getName方法。后两种形式引用的是Attr类的构造器，其中一个构造器接受的是字符串引元，而另一个构造器接受的则是字符串和对象。当构造器或方法有重载版本时，我们必须指定想要引用的重载版本的引元。

接下来的@see形式将读者引向了一个特定的包:com.magic.attro

最后的两种形式使得我们可以引用其他的文档。其中，前一个使用了来定义链接，后一个使用引号将文档名括了起来。我们可以用这两种形式将读者引向其他文档，例如完整的说明书。

对语言实体进行命名的@ see形式(除上面的最后两种形式之外的所有形式)可以在实体的后面跟随一个标号(label)。在生成的文档中，这种标号的名字将会替代实体的名字。例如:

```
@ see #getNameAttribute Names
```

将创建一个指向getName的文档的链接，但它所显示的文本是”Attribute Names”而不是"getName"。通常我们应该使用成员的真实名字，但这里所展示的特性偶尔也会很有用。