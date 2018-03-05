
<h2>安装Redis</h2>

1.	安装依赖
yum install gcc-c++

2.	下载redis包  解压  tar –zxvf  xxx.tar.gz

3.	解压之后进入解压出来的文件夹 执行make命令

4.	安装 make PREFIX=/usr/local/redis install

5.	将解压的文件夹中的redis.conf  拷贝至安装目录 cp  redis.conf  /usr/local/redis

6.	拷贝之后  将redis.conf中的 daemonize no改为 daemonize yes

7.	后台启动redis   ./bin/redis-server ./redis.conf

8.	关闭  ./bin/redis-cli shutdown

9.	进入redis控制台  ./bin/redis-cli


<h2>Redis的数据结构之字符串</h2>
1.get 

2.set 

3.getset (先get之后再set)

4.incr key  :key对应的value值+1 ，如果本来没有这个key，则value先默认0再+1
		 如果有这个key,但是value不是数字类型的，则报错
5.Decr key  : key对应的value值-1 ，如果本来没有这个key，则value先默认0再-1
		 如果有这个key,但是value不是数字类型的，则报错

6.incrby num 5:使num对应的value值+5；如果本来没有这个key，则value先默认0再+5
		   如果有这个key,但是value不是数字类型的，则报错
7.incrby num 5：与上相反

8.append num 3：在num这个key对应的value后追加一个3，如果本来没有，则设置为3

<h2>Redis的数据结构之哈希</h2>
1.设置值
Hset key field value:  如hset myhash username jack ， hset myhash password jack


2.取值：
Hget key field:  如hget myhash username

3.一次性设置多个值：
Hset key field value field value：如hmset myhash2 username rose age 10

4.一次性取多个值：
Hmget key field,field…: 如hmget myhash2 username age

5.取所有值：
Hgetall key: 如hgetall myhash2

6.删除某个属性的value：
Hdel key field [field…] :如hdel myhash2 username

7.删除整个对象：
Del key : 如del myhash2 ,将整个myhash2的哈希表删除

8.增加value值：
Hincrby key field increment: 如hincrby myhash2 age 10，将age的值增加10

9.判断某个属性是否存在:
Hexists key field： 如hexists myhash2 age，返回1说明存在

10.获得哈希的长度：
Hlen key: 如hlen myhash ,返回长度2

11.获得key中所有属性：
Hkeys key: 如hkeys myhash

12.获得key中所有属性的值：
Hvals key: 如hvals myhash






<h2>Redis的数据结构之LIST</h2>


1.	从左边向list中添加数据：
lpush key value [value] , 如：  lpush mylist a b c

2.	从右边向list中添加数据：
rpush key value [value] , 如：  lpush mylist2 a b c

3.	查看list中的内容：
lrange key start stop:
lrange mylist 0 5:查看mylist中第一个到第六个的数据

lrange mylist 0 -2 :查看mylist中第一个到到第二个数据


4.	弹出链表中的元素：
lpop key:从左边弹出某个list的头元素  lpop mylist

rpop key:从右边弹出list中的元素  rpop mylist2


5.	查看list的长度：
llen key: 如llen mylist

6.	链表存在就插入，不存在报错，与第一个插入不同，第一个插入，链表不存在会创建
lpushx key value


7.	删除操作
lrem key count value:从链表中删除count个value
lrem mylist3 2 3：从mylist3中从左边删除两个3
lrem mylist3 -2 1：从mylist3中从右边删除两个1
lrem mylist3 0 2： 从mylist3中删除所有2 

8.	将链表中某个位置的值替换：
lset key index value:
lset mylist3 1 hahah , 如果越界会报错

9.	在list的某个元素之前或者之后插入数据
linsert key before|after pivot value:
linsert mylist4 before b 2: 在mylist4的b之前插入一个2

10.
将mylist5从右边弹出一个数据，从左边插入到mylist6中
rpoplpush mylist5 mylist6
使用场景：作为消息队列，首先将一个消息备份到另一个链表中，防止处理该消息出错后，消息丢失的问题


<h2>Redis的数据结构之SET</h2>

使用场景：
跟踪一些唯一数据
用于维护数据对象之间的关联关系
与list不同，set中不允许出现重复元素

1.添加元素
sadd key member [ member ],
如sadd myset 1 2 3

2.删除元素
srem key member [ member ]
如srem myset 1 

3.查看SET中的所有元素
smembers key
如smembers myset

4.判断某个值是不是存在SET中
sismember key member
sismember myset s，判断myset中是否存在s

5.
差集，并集，交集
sdiff key key     差集
sinter key key    并集
sunion key key   交集

6.查询set中的数量
scard key

7.将差集，交集，并集存入到另一个set中
sdiffstore destination key [key ...]
sinterstore destination key [key ...]
sunionstore destination key [key ...]



<h2>Redis的数据结构之Sorted-Set</h2>

用于游戏的排名，微博的热搜，因为有分数

1.新增，如果本来有值了，会用新的分数替换旧的分数
zadd key  score member [score member ...]
如：zadd mysort 70 zs 80 ls 90 ww


2.指定成员进行删除
zrem key member [member ...]
zrem mysort tom ww

按照排名进行删除，把前几个给删除了
zremrangebyrank key start stop

给定分数范围进行删除
zremrangebyscore key min max

3.查看元素个数
zcard key
zcard mysort


4.查看数据，如果带有WITHSCORES，按照分数由小到大的顺序输出
zrange key start stop [WITHSCORES]
zrange mysort 0 -1
zrange mysort 0 -1 withscores

按照分数由大到小的顺序输出：
zrevrange key start stop [WITHSCORES]

根据分数范围进行查找，limit指从第几个开始，一共显示几个数
zrangebyscore key min max [WITHSCORES] [LIMIT offset count]

查看某个成员的分数
zscore key member
zincrby mysort 10 ls

增加某个成员的分数
zincrby key increment member

查看某个分数段的人数
zcount key min max




<h2>Redis中keys的一些操作</h2>

1.查看key
keys pattern
   如查看所有的key:   keys *
   查看以my开头的key:  keys my*
2.删除key
del key [key ...]
如删除my1: del my1
3.判断一个key是否存在
exists key [key ...]

4.重命名一个key
rename key newkey

5.设置过期时间
expire key seconds
设置num1000s之后过期： expire num 1000


<h2>Redis的一些特性</h2>


1.Redis中有16个数据库0-15 , 默认使用第一个数据库
选择其它数据库，使用第二个数据库，使用select 1 
2.将一个数据库中的数据移入另一个数据库
move key db
将name这个key移入第二个数据库： move name 1
3.multi  类似于开启事务，将指令放入到一个队列中，并不执行
exec   类似于提交事务，执行队列中的指令
discard 类似于回滚，清空队列中的指令
Redis的持久化

RDB持久化
AOF持久化


