Android的Nexus搭建Maven私有仓库

![](http://oxp6pf88h.bkt.clouddn.com/nexuslogo.png)

> 前言

我们平时在开发的时候总会compile一些远程仓库的框架来使用，但不可避免的是有些公司是内网，或是断网了，这就造成了依赖失败，亦或是自己开发了一个很牛的组件，希望同步给小伙伴一块耍耍，但是网络环境不允许，总不能拿个U盘拷贝过去吧，被别人听到程序员是这么协作的，估计会笑掉大牙。
Nexus这个私服正好解决了这个问题，他的思路是本地与远程之间嫁接一层本地的服务器，对于公司内部团队协作开发的，我们可以将自己的组件上传到私服上，同一个局域网下，供小伙伴们依赖，避免了直接与远程仓库对接。
 
所以，这一次就来教大家如果搭建一个属于自己的本地仓库，然后通过gradle将自己的library上传到自己的仓库，供小伙伴们使用


>环境搭建

- 下载链接:[https://www.sonatype.com/download-oss-sonatype](https://www.sonatype.com/download-oss-sonatype)

- 我们选择2.X版本的，下载红线指向的zip包

  ![](http://oxp6pf88h.bkt.clouddn.com/nexus1.png)

- 解压看下路径

 ![](http://oxp6pf88h.bkt.clouddn.com/nexus2.png)
 
 如果是win系统的话，可以点击bin目录下面的jsw目录，选择自己对应的平台，然后点击start-nexus.bat批处理文件就可以运行起本地的私服，我当前的系统环境mac，只需要在Terminal下cd到bin目录下面，通过命令**“./nexus start”** 就可以开启私服，当然，与之对应的关闭私服命令是**"./nexus stop"**
 
 ![](http://oxp6pf88h.bkt.clouddn.com/nexus3.png)
 
- 在浏览器中是输入[http://localhost:8081/nexus/](http://localhost:8081/nexus/),点击右上角的log in进行登录，默认的用户名是admin，密码是admin123，
 
 ![](http://oxp6pf88h.bkt.clouddn.com/nexus4.png)
 
- 第1处，点击左边的菜单栏的Repositories，可以查看默认的几个仓库
 
 ![](http://oxp6pf88h.bkt.clouddn.com/nexus5.png)
 
  nexus的仓库Type分为以下四种：
        - group: 仓库组
        - hosted：宿主
        - proxy：代理
        - virtual：虚拟
 
   第3处的链接就是我们仓库的地址，我们后面会根据这个地址，将library上传到该地址的仓库中去
   
   接下来，我们创建一个自己仓库，点击第2处"Add"选择"Hosted Repository",在新建的面板输入ID和Name，Deployment Policy一定要选择Allow Redeploy，红色箭头那个部分，部署策略设置为允许重新部署，因为我们的库肯定会频繁修改和提交，点击save就可以保存
   
   ![](http://oxp6pf88h.bkt.clouddn.com/nexus6.png)
   
   在仓库列表的中就会出现codelang仓库，我们点击看一下，当前仓库是没有什么文件的
   
   ![](http://oxp6pf88h.bkt.clouddn.com/nexus7.png)
   
   
> 上传文件到仓库

  上传到仓库有两种方式，一种是手动方式，一种是通过gradle上传的方式，我们先来看看手动方式
  
#### 手动方式:
  
  我们点击codelang仓库，在仓库详情的tab中，选择Artifact Upload一栏
  
  ![](http://oxp6pf88h.bkt.clouddn.com/nexus11.png)
  
  - 第1处，我们选择AGV Parameters来定义我们的资源
  - 第2处，设置组Group，何为Group呢？比如，我们依赖Retrofit框架 compile 'com.squareup.retrofit2:retrofit:2.1.0' ，那么"com.squareup.retrofit2"就是组，我们在依赖的时候会用到它
  - 第3处，设置Artifact，Artifact和第二处的Group一样，两个冒号中间的"retrofit"就是Artifact，一般，我们用它来定义库的名称
  - 第4处，设置Version版本号，和第二处的例子"2.1.0"一样
  - 第5处，设置包的类型，就是我们依赖的库是什么类型的
  - 第6处，选择我们上传的文件
  
  ![](http://oxp6pf88h.bkt.clouddn.com/nexus12.png)
  
  - 第7处，将这个库添加到待上传区域，这个地方可以多次选择添加文件，我们将支付宝jar和微信jar一同上传，一般有支付类的app都会有他们俩存在，所以，我们将他两捆绑在一起，以后就只需依赖一个文件就可以了
  - 第8处，我们点击"Upload Artifact"进行上传到仓库
  
  
上传成功后，我们点击仓库列表codelang仓库后面的链接，你就会看见我们刚刚定义的库，我们一直点下去看看
  
  ![](http://oxp6pf88h.bkt.clouddn.com/nexus13.png)
  
对于java web开发，通过pom来依赖Maven库的，我们可以通过仓库详情页的Browser Index 一栏来查看

  ![](http://oxp6pf88h.bkt.clouddn.com/nexus14.png)
  
#### gradle上传
    
 经过上面的一番手动上传，我们应该对上传到仓库有了一定的了解。接下来，我们用gradle的方式，将我们的library库上传到仓库，供我们的小伙伴们集成.(在组件化开发模式下，我们一般都要将业务组件打包成aar文件上传到仓库，供空壳app集成)

 1、新建一个Module，选择Android Library，取名叫baseLib，用来存放一些基类
 
 ![](http://oxp6pf88h.bkt.clouddn.com/nexus15.png)
 
 2、第1处，打开项目的gradle.properties文件,第二处，配置上传的参数，供gradle读取
 
 ![](http://oxp6pf88h.bkt.clouddn.com/nexus17.png)
 
 看到这些配置很熟悉吧，和手动上传传递的参数一样
 
 3、接下来，我们打开baseLib的build.gradle文件，配置一下上传到maven的代码，然后gradle运行一下
 
```
 dependencies {
    ...
 }
 apply plugin: 'maven'
 uploadArchives {
     configuration = configurations.archives
     repositories {
         mavenDeployer {
             repository(url: MAVEN_URL) {
                 authentication(userName: NEXUS_USERNAME, password: NEXUS_PASSWORD)
             }
             pom.project {
                 groupId GROUP
                 artifactId ARTIFACT
                 version VERSION
                 packaging TYPE
                 description DESCRIPTION
             }
         }
     }
 }
 
 artifacts {
     archives file('baselib.aar')
 }

```

4、我们在baseLib里面写个BaseActivity基类

```
public  abstract  class  BaseActivity  extends  Activity {
    private Toast mToast;

    @Override
    protected  void  onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        initView();
    }

    public  void  showToast(String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 布局id
     *
     * @return int
     */
    protected  abstract int  getLayoutId();

    /**
     * 初始化view
     */
    protected  abstract void  initView();
}

```
 
5、点击build菜单栏下面的ReBuild Project生成aar文件，项目默认是不会生成aar文件的
  将项目切换到Project模式下面，就可以看到生成的aar文件
 
 ![](http://oxp6pf88h.bkt.clouddn.com/nexus18.png)
 
6、点击项目最右边的gradle侧边栏，展开baselib,打开upload，双击运行uploadArchives进行上传，下面没有报错，那就说明上传成功

 ![](http://oxp6pf88h.bkt.clouddn.com/nexus19.png)
 
7、打开远程仓库看看我们的arr文件,和我们手动上传的想效果一样，完美

 ![](http://oxp6pf88h.bkt.clouddn.com/nexus20.png)
 
 
>使用

 现在，我们开始使用它吧
 
 我们随便新建一个项目，然后打开项目的build.gradle文件，输入maven的地址，也就是我们codelang仓库的地址
 
```
allprojects {
    repositories {
        jcenter()
        maven{ url 'http://localhost:8081/nexus/content/repositories/codelang'}
    }
}
```

然后，我们打开app的build.gradle文件，还记得依赖库的命名方式吗? **"Group:Artifact:version"**

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:26.+'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    testCompile 'junit:junit:4.12'
    
    //添加依赖库
    compile 'com.codelang.baseLib:baseLib:1.0.0'
    compile 'codelang:pay:1.0.0'
}
```

gradle编译一下，然后我们在MainActivity使用一下,完美

![](http://oxp6pf88h.bkt.clouddn.com/nexus21.png)

>总结

最近一直在看组件化方面的知识，顺便把仓库给撘一下，虽然是一个人开发，但也要模拟一下多人开发的感觉，万一以后进大公司了咋搞(稍微的意淫一下)。

虽然当前的环境很糟糕，但还是要为以后的离开做好准备

<center><p>关注公众号codelang<p>

![](https://user-gold-cdn.xitu.io/2017/12/8/160342f2d589ab61?w=170&h=169&f=png&s=33128)</center>