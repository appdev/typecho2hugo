# typecho2hugo


将 typecho 博客内容转为 hugo 需要的markdown，并将原来的图片上传到图床


## 使用方法

### 链接 typecho 数据库

> 因为我用的是 sqlite 如果你同样也是 sqlite 将数据库放在一下目录即可

![](screenshots/13.png)

使用 Mysql 的修改这里的

### 修改图床配置

目前是将图片上传到 gitee 图床，修改下面的配置即可  

如果需要其他图床，也可以修改`uploadImageToGitee`方法

![](screenshots/12.png)

