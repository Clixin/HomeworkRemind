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