# 云服务器 ECS Linux SWAP 配置概要说明
# [SWAP 简介]()

Linux 中的 SWAP（交换分区），类似于 Windows 的虚拟内存。系统会把一部分硬盘空间虚拟成内存使用，将系统内非活动内存换页到 SWAP，以提高系统可用内存。

注：参阅 [ECS 使用须知](https://help.aliyun.com/document_detail/25430.html?spm=a2c4g.11186623.2.3.BQ3xsr)，云服务器 ECS 如果您使用普通云盘，不建议使用swap分区。如果是高效云盘或SSD云盘，可以根据实际情况使用swap分区。

# [SWAP 配置介绍及 FAQ]()

- [开启 SWAP](https://help.aliyun.com/knowledge_detail/42534.html#%E5%BC%80%E5%90%AF%20SWAP)
- [关闭 SWAP](https://help.aliyun.com/knowledge_detail/42534.html#%E5%85%B3%E9%97%AD%20SWAP)
- [CentOS 使用 mkswap 格式化文件时报错的处理方法](https://help.aliyun.com/knowledge_detail/42534.html#Centos%20%E4%BD%BF%E7%94%A8%20mkswap%20%E6%A0%BC%E5%BC%8F%E5%8C%96%E6%96%87%E4%BB%B6%E6%97%B6%E6%8A%A5%E9%94%99%E7%9A%84%E5%A4%84%E7%90%86%E6%96%B9%E6%B3%95)

## [开启 SWAP]()

1、创建用于交换分区的文件：

```
dd if=/dev/zero of=/mnt/swap bs=block_size count=number_of_block  
```

**注**：block_size、number_of_block 大小可以自定义，比如 bs=1M count=1024 代表设置 1G 大小 SWAP 分区。

2、设置交换分区文件：

```
mkswap /mnt/swap
```

![img](https://img.alicdn.com/tfscom/TB1IY7CKXXXXXavaXXXXXXXXXXX)

3、立即启用交换分区文件

```
swapon /mnt/swap
```

![img](https://img.alicdn.com/tfscom/TB1pmRcKpXXXXanXXXXXXXXXXXX)

**注**：如果在 /etc/rc.local 中有* **swapoff -a* 需要修改为 *swapon -a *

 ![img](https://img.alicdn.com/tfscom/TB1oUEFKXXXXXauaXXXXXXXXXXX)

4、设置开机时自启用 SWAP 分区：

需要修改文件 /etc/fstab 中的 SWAP 行，添加

```
/mnt/swap swap swap defaults 0 0
```

![img](https://img.alicdn.com/tfscom/TB1ecwBKXXXXXczaXXXXXXXXXXX)

**注**：/mnt/swap 路径可以修改，可以根据创建的 SWAP 文件具体路径来配置。

5、修改 swpapiness 参数

在 Linux 系统中，可以通过查看 /proc/sys/vm/swappiness 内容的值来确定系统对 SWAP 分区的使用原则。当swappiness 内容的值为 0 时，表示最大限度地使用物理内存，物理内存使用完毕后，才会使用 SWAP 分区。当swappiness 内容的值为 100 时，表示积极地使用 SWAP 分区，并且把内存中的数据及时地置换到 SWAP 分区。

查看修改前为 0，需要在物理内存使用完毕后才会使用 SWAP 分区：

![img](https://img.alicdn.com/tfscom/TB1Nxo3KXXXXXbUXpXXXXXXXXXX)

可以使用下述方法临时修改此参数，假设我们配置为空闲内存少于 10% 时才使用 SWAP 分区：

```
echo 10 >/proc/sys/vm/swappiness
```

![img](https://img.alicdn.com/tfscom/TB13LA1KXXXXXczXpXXXXXXXXXX)

若需要永久修改此配置，在系统重启之后也生效的话，可以修改 /etc/sysctl.conf 文件，并增加以下内容：  

```
# vim /etc/sysctl.confvm.swappiness=10# sysctl -p
```

## [关闭 SWAP]()

当系统出现内存不足时，开启 SWAP 可能会因频繁换页操作，导致 IO 性能下降。如果要关闭 SWAP，可以采用如下方法。

1、*free **-m *查询 SWAP 分区设置：

![img](https://img.alicdn.com/tfscom/TB1nT3WKXXXXXaoXFXXXXXXXXXX)

2、使用命令 swapoff 关闭 SWAP，比如：

```
swapoff /mnt/swap  
```

![img](https://img.alicdn.com/tfscom/TB1XcMLKXXXXXb5XVXXXXXXXXXX)

3、修改 /etc/fstab 文件，删除或注释相关配置，取消 SWAP 的自动挂载：

![img](https://img.alicdn.com/tfscom/TB1IPVeKpXXXXXyXXXXXXXXXXXX)

4、  通过 *free -m  *确认 SWAP 已经关闭。

![img](https://img.alicdn.com/tfscom/TB1jj3OKXXXXXXEXVXXXXXXXXXX)

5、 swappiness 参数调整：

可以使用下述方法临时修改此参数，这里配置为 0%：

```
echo 0 >/proc/sys/vm/swappiness    
```

若需要永久修改此配置，在系统重启之后也生效的话，可以修改 /etc/sysctl.conf 文件，并增加以下内容：

```
# vim /etc/sysctl.confvm.swappiness=0# sysctl -p
```

## [Centos 使用 mkswap 格式化文件时报错的处理方法]()

### 问题现象

使用 mkswap 创建 SWAP 时出现类似如下报错信息：

```
mkswap: error: swap area needs to be at least 40 KiB
```

![img](https://img.alicdn.com/tfscom/TB1DCsCKXXXXXcaaXXXXXXXXXXX)

### **问题原因**

指定的 SWAP 文件太小，SWAP 文件至少应该大于 40KB。

### **解决方法**

重新生成更大的文件格式化为 SWAP 即可。