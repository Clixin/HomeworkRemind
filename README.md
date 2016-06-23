# HomeworkRemind
An App can remind you homework

##版本0.01
完成RecyclerView和CardView布局
初步如下图
![demo1.png](http://upload-images.jianshu.io/upload_images/1904896-d3a036534b918a24.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##版本0.02
完成Fab悬浮按钮能手动添加课程
界面如下图
![demo2.png](http://upload-images.jianshu.io/upload_images/1904896-37b9f2c2a969642b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##版本0.021
1. 修复已知bug
	* `TimePickerDialog`选择19点以后的小时之后，点确定，会导致数组下标越界
	原因不知，
	删除`c.set(hourOfDay, minite)`，即解决

2. 优化选择时间和日期后的文字大小颜色， 比原字体稍微大，稍微暗

3. 添加javadoc注释文档，并格式化代码

##版本0.03
1. 储存Homework，当前问题：退出重进后，添加的课程都没了
2. 利用文件储存，用count来计数，将每个homework单独存一个文件里，单独取出来
3. 初步完成

##版本0.04
1. 点击卡片查看详情
2. 界面如下
![demo3.png](http://upload-images.jianshu.io/upload_images/1904896-ccb04900f0761e9c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##版本0.05
1. 重构代码，对作业的增删改操作提取出来
2. 优化代码可读性
3. 增加删除功能，但是有个bug，由于存储模块没设计好，删除文件还没搞定
4. 增加修改功能，bug还没搞定

##版本0.06
1. 改用SharedPreferences来存储
2. 首先存储Homework的id
3. 再根据id单独存储Homwork里的内容
4. 读取的时候，先读取ID，再根据id读取里面内容
5. 重构代码