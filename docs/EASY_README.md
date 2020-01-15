# 目录索引
- [EasyGuideLayer](#easyguidelayer): 界面蒙层引导组件
- [EasySharedPreferences](#easysharedpreferences): SharedPreferences存取组件
- [EasyDimension](#easydimension): 尺寸转换组件
- [EasyFormatter](#easyformatter): 数据格式化排版组件
- [EasyLog](#easylog): 日志打印组件
- [EasyToast](#easytoast): Toast通知组件
- [EasyReflect](#easyreflect): 反射操作组件
- [EasyActivityResult](#easyactivityresult): onActivityResult解耦组件
- [EasyPermissions](#easypermissions): 动态权限申请组件
- [EasyExecutor](#easyexecutor): 线程池封装组件
- [EasyBundle](#easybundle): Bundle数据存取组件
- [EasyPhoto](#easyphoto): 从拍照、图库进行图片选择组件。
- [EasyImageGetter](#easyimagegetter): TextView加载html标签时，提供`img`标签的图片加载功能
- [MVP](#mvp): 简单MVP架构



### [EasyGuideLayer](./EasyGuideLayer.md)

> [点我查看完整使用文档](./EasyGuideLayer.md)
- 链式调用。调用逻辑清晰直观
- 支持同时设置多个引导层
- 支持高亮区域的自定义绘制
- 支持高亮区域点击监听
- 支持指定任意View设置蒙层引导
- 支持进行蒙层展示、隐藏事件监听

使用示例：

```
// 创建引导层示例，并为引导层添加指定配置
val item = GuideItem.newInstance(view, padding)
    .setLayout(guideLayout)// 设置引导View
    .setGravity(Gravity.LEFT)// 设置引导View位置
    .setOffsetProvider(provider)// 设置用于进行引导View的位置调整
    .setHighLightShape(GuideItem.SHAPE_RECT)// 设置高亮区域绘制模式
    .setOnViewAttachedListener(listener)// 设置引导View被添加到蒙层布局中时的通知监听
    .setOnDrawHighLightCallback(callback)// 这是自定义高亮块绘制回调
    .setOnHighLightClickListener(listener)// 设置高亮点击回调监听


// 创建蒙层实例并绑定引导层，进行展示：
EasyGuideLayer.with(activity)
    .setBackgroundColor(color)// 蒙层背景色
    .setOnGuideShownListener(lambda)// 蒙层展示、消失监听
    .addItem(item)// 绑定引导层实例
    .setDismissOnClickOutside(false)// 设置点击到蒙层上的非点击区域时，是否自动让蒙层消失
    .setDismissIfNoItems(true)// 设置当蒙层中一个引导层实例都没绑定时，自动让蒙层消失
    .show()// 展示蒙层
```

### [EasyImageGetter](./EasyImageGetter.md)

> [点我查看完整使用文档](./EasyImageGetter.md)
- 支持设置`placeholde`图片加载时占位图
- 支持设置`error`图片加载失败时的占位图
- 支持指定uri进行加载。不仅仅局限于网络图片。还包括加载本地图片、assets图片等。
- 支持自定义加载器：满足各种加载需求。

用户实例：

```
// 提供html文本数据
private val html =
"""
<h5>asset图片加载示例</h5>
<img src="file:///android_asset/imagegetter/cat.png">
<h5>http图片加载示例</h5>
<img src="http://www.w3school.com.cn/i/eg_tulip.jpg">
""".trimIndent()

// 提供展示的TextView控件
private val textView:TextView = getTextView()

EasyImageGetter.create()
    // 设置图片加载时的占位图
    .setPlaceHolder(R.drawable.placeholder)
    // 设置图片加载失败时的占位图
    .setError(R.drawable.error)
    // 指定加载的html与tv控件即可
    .loadHtml(html, textView)

```

### [EasyPhoto](./EasyPhoto.md)

> [点我查看完整使用文档](./EasyPhoto.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyPhoto.kt)

> 从图库或者使用相机拍照获取图片的组件。

贡献者：[Vicent9920](https://github.com/Vicent9920)

- 支持链式调用
- 支持图片输出到指定地址
- 支持任意线程选择进行图片选择

用户实例：

```
val photo = EasyPhoto()// 创建EasyPhoto实例
    // 是否需要进行裁剪
    .setCrop(true|false)
    // 指定创建的图片地址。
    .setImgPath(imgPath:String)

// 通过设置回调，获取选择到的文件
photo.setCallback { file:File ->
    // TODO 使用选择的文件进行操作
}

// 当然。不排除出现非预期的问题。所以也提供了错误回调
photo.setError { error:Exception ->
    // TODO
}

// 跳转拍照并获取图片
photo.takePhoto(activity)

// 或者跳转图库进行图片选择
photo.selectPhoto(activity)
```

### [EasySharedPreferences](./EasySharedPreferences.md)

> [点我查看完整使用文档](./EasySharedPreferences.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasySharedPreferences.kt)

> 使用一个具体的实体类，进行SharedPreferences数据存取

- 通过具体的实体类进行SP数据存储操作。避免`key值硬编码`
- 自动同步，即使别的地方是`直接使用SharedPreferences进行赋值`，也能自动同步相关数据。
- 打破SharedPreferences限制。支持几乎任意类型数据存取

用法示例：

```
// 以SharedPreferences文件名为user_info，存储数据为username, age, address为例

// 1. 创建映射实体类
@PreferenceRename("user_info")
class User:PreferenceSupport() {
    var username:String
    var age:Int
    var address:String
}

// 2. 进行读取
val user = EasySharedPreferences.load(User::class.java)

// 3. 进行修改

// 直接使用load出来的user实例进行数值修改
user.age = 16
user.username = "haoge"

// 修改完毕后，apply更新修改到SharedPreferences文件。
user.apply()
```

### [EasyDimension](./EasyDimension.md)

> [点我查看完整使用文档](./EasyDimension.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyDimension.kt)

> 用于灵活的进行设备尺寸单位转换
> 
> 支持全尺寸数值转换。

用法示例

```
// 转换10dp到px
EasyDimension.withDIP(10).toPX()
// 转换30sp到MM
EasyDimension.withSP(30).toMM()
```

### [EasyFormatter](./EasyFormatter.md)

> [点我查看完整使用文档](./EasyFormatter.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyFormatter.kt)

> 用于对任意类型数据，进行格式化输出排版，结合log打印组件使用，使log输出展示更清晰

- 支持对`Set/List/Map/JSON/POJO`数据进行格式化排版
- 支持最高长度过滤：避免打印超长数据时造成版面浪费

用法示例：

```
// 创建待格式化数据
val any:Any = create()
// 使用formatter实例进行格式化
val result:String = EasyFormatter.DEFAULT.format(any)
```

### [EasyLog](./EasyLog.md)

> [点我查看完整使用文档](./EasyLog.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyLog.kt)

> 用于简单的进行日志打印输出，支持格式化输出、自定义打印格式。

- 不阻塞：打印任务运行于独立线程中，避免大量打印数据时造成UI阻塞。
- 安全: 对打印任务做好了异常处理。不用担心出现crash问题
- 使用EasyFormatter对任意数据进行格式化排版
- 支持添加自定义规则
- 灵活、直观的进行输出样式定制
- 自动适配TAG. 也可手动指定。
- 使用开关。关闭线上包的日志输出。
- 使用'上边界'逻辑进行栈帧匹配，支持二次封装使用

用法示例：

```
val any:Any = create()// 创建待打印数据
EasyLog.DEFAULT.d(any)// 使用默认log实例进行数据打印. 以Log.d()的方式进行输出
```

### [EasyToast](./EasyToast.md)

> [点我查看完整使用文档](./EasyToast.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyToast.kt)

> 用于进行Toast提示，可很简单的指定输出样式。

1. 支持在任意线程下进行toast提示
2. 非常方便的进行任意样式的定制
3. 不管当前是否正在展示之前的数据。有新消息通知时，直接展示新消息，无需等待

博客地址：https://juejin.im/post/5b0638336fb9a07aa9261ce6

用法示例：

```
val message:String = create()// 创建待提示数据
EasyToast.DEFAULT.show(message)// 使用系统样式进行输出
EasyToast.create(layoutID:Int, tvID:Int, duration:Int).show(message)// 使用自定义样式进行输出
```

### [EasyReflect](./EasyReflect.md)

> [点我查看完整使用文档](./EasyReflect.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyReflect.kt)

> 对常规的反射操作进行封装。达到更便于使用反射的效果

博客地址：https://juejin.im/post/5b1de20c6fb9a01e701000cb

用法示例：

```
// 以类名Test为例
class Test private constructor(private val name:String) {
    private fun wrap(name:String):String = "包裹后的数据$name"
}

// 创建Reflect实例：
var reflect = EasyReflect.create(Test::class.java).instance("默认参数")

// 为name字段赋值：
reflect.setField("name", "EasyReflect")
// 读取name字段的值："EasyReflect"
val value = reflect.getValue("name")

// 调用方法wrap方法，并传入参数value
reflect.call("wrap", value)
// 调用wrap方法，并获取返回值: "包裹后的数据EasyReflect"
val result = reflect.callWithReturn("wrap", value).get<String>()
```

### [EasyActivityResult](./EasyActivityResult.md)

> [点我查看完整使用文档](./EasyActivityResult.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyActivityResult.kt)

>用于解决onActivityResult业务逻辑臃肿的问题

- **业务解耦分离**: 各自启动业务线处理各自的回调逻辑
- **去除requestCode**: 进行启动时自动生成随机的requestCode, 不用再为每个启动任务分别配置请求码了。
- **防暴击**: 防止快速点击时启动多个重复页面

博客地址：https://juejin.im/post/5b21d019e51d4506d93701ba

用法示例：

```
EasyActivityResult.startActivity(activity, Intent(activity, DemoActivity::class.java),
        { resultCode:Int, data:Intent? ->
            // 直接在此进行返回数据处理
        })
```

### [EasyPermissions](./EasyPermissions.md)

> [点我查看完整使用文档](./EasyPermissions.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyPermissions.kt)

> 进行6.0+的动态权限请求

- 链式调用
- 支持定制权限申请说明弹窗
- 支持同时申请多个权限
- 多权限申请时进行去重与空过滤
- 自动使用顶层Activity执行权限请求
- 支持在任意线程进行权限申请
- 当有默认拒绝的权限时，提示用户前往权限设置页。手动开启权限

博客地址：https://juejin.im/post/5b1a2a326fb9a01e5d32f208

用法示例：

```
EasyPermissions.create(// 指定待申请权限
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_CALENDAR,
    Manifest.permission.WRITE_CONTACTS
    )
    // 定制权限申请说明弹窗
    .rational { permission, chain ->
        AlertDialog.Builder(this)
                .setTitle("权限申请说明")
                .setMessage("应用需要此权限：\n$permission")
                .setNegativeButton("拒绝", {_, _ -> chain.cancel() })
                .setPositiveButton("同意", {_, _ -> chain.process() })
                .show()

        return@rational true
    }
    // 设置授权结果回调
    .callback { grant ->
        EasyToast.DEFAULT.show("权限申请${if (grant) "成功" else "失败"}")
    }
    // 当权限被默认拒绝时。调起弹窗提醒需要用户去主动开启权限
    .alwaysDenyNotifier(object : PermissionAlwaysDenyNotifier() {
        AlertDialog.Builder(activity)
                .setTitle("权限申请提醒")
                .setMessage("以下部分权限已被默认拒绝，请前往设置页将其打开:\n\n")
                .setPositiveButton("确定", { _, _ ->  goSetting(activity)})
                .setNegativeButton("取消", {_,_ -> cancel(activity)})
                .show()
    })
    // 发起请求
    .request()
```

### [EasyExecutor](./EasyExecutor.md)

> [点我查看完整使用文档](./EasyExecutor.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyExecutor.kt)

> 用于进行`安全`、`高效`、`便利`的线程池操作功能组件

- **安全**: 直接catch住任务执行期间出现的异常。并通知给用户，避免出现crash
- **回调通知**: 执行任务期间，有分别的生命周期作为通知。
- **配置灵活**: 可方便、灵活的对每次所启动的任务，配置线程名、回调等。
- **任务延迟**: 支持在每次启动任务前。指定延迟时间
- **异步任务**: 支持直接启动异步任务并回调传递数据
- **线程切换**: 支持指定回调方法所在的线程。默认为运行于UI线程中

用法示例

```
// 1. 第一步：创建示例
val executor =
    // size为所需创建的线程池的大小。当size <= 0时。
    // 表示需要使用newCachedThreadPool。
    EasyExecutor.newBuilder(size)
            .setName(name)// 默认的线程名
            .setPriority(priority)// 线程池中创建线程的优先级
            .onStart {threadName -> } // 默认任务启动时的回调
            .onSuccess {threadName -> }// 默认任务执行完毕后的回调
            .onError {threadName, throwable -> }// 默认任务执行出现异常时的回调
            .setDeliver(deliver)// 默认的回调任务派发器。用于将信息派发到指定线程去。
            .build()// 最后。执行创建

// 2. 第二步：启动任务
executor.execute(runnable:Runnable)// 启动普通任务

// 启动异步回调任务
executor.asyncResult(result:(T) -> Unit) // 先配置任务回调
    .asyncTask(task:(Notifier) -> T)// 配置后台执行任务

executor.setDelay(delay).execute(runnable)// 延时启动任务
```

### [EasyBundle](./EasyBundle.md)

> [点我查看完整使用文档](./EasyBundle.md)

> [点击下载组件源码文件进行使用](https://raw.githubusercontent.com/yjfnypeu/EasyAndroid/master/utils/src/main/java/com/haoge/easyandroid/easy/EasyBundle.kt)

> 用于使Bundle数据存取操作变得`简单`、`方便`、`灵活`、`强大`

1. 简化Bundle数据存取api：
2. 打破Bundle数据格式限制。支持对非可序列化对象进行存取。
3. 支持注入操作。在进行页面跳转传值时。将会非常好用。

博客地址：https://juejin.im/post/5b2c65bde51d45587d2dd86f

用法示例：

```
// 1. 存储任意数据对象到bundle中去
EasyBundle.create(bundle)// 绑定bundle容器
    .put(key, value)// 指定任意数据进行存储。包括非可序列化对象

// 2. 从bundle中读取并自动转换为具体对象数据
EasyBundle.create(bundle).get<User>("user")

// 3. 支持数据自动注入
class ExampleActivity:BaseActivity() {
    // 从intent中读取name数据并注入到name字段中去
    @BundleField
    var name:String? = null
}
```

### [MVP](./MVP.md)

> [点我查看完整使用文档](./MVP.md)

博客地址：https://juejin.im/post/5b4ee27ff265da0f98314f01

> 提供的一种简单的MVP分层架构实现。

- 支持单页面绑定多个Presenter进行使用
- 支持P层进行生命周期派发

用法示例

```
// 1. 创建页面通信协议接口：
interface CustomView:MVPView {
    // 定义通信协议方法：P层将使用此方法驱动V层进行界面更新
    fun updateUI()
}

// 2. 创建Presenter。并与对应的通信协议相绑定：
class CustomPresenter(view:CustomView):MVPPresenter(view) {
    // 直接创建对应的启动方法，供V层进行启动调用
    fun requestData() {
        // TODO 进行数据业务处理。并在处理完成后，通过view通知到V层
    }
}

// 3. 创建具体的V层(Activity or Fragment),并绑定Presenter进行使用：
class CustomActivity:BaseMVPActivity, CustomView {

    // 创建presenter并绑定。可创建多个persenter使用
    val persenter = CustomPresenter(this)
    override fun createPresenters() = arrayOf(presenter)
    override fun updateUI() {// TODO 进行界面更新}

    fun initPage() {
        // 通过绑定的Presenter发起任务
        presenter?.requestData()
    }
}
```

## 混淆配置

部分组件需要进行混淆配置。请注意别遗漏了：

```
# EasyBundle
-keepclasseswithmembernames class * {
    @com.haoge.easyandroid.easy.BundleField <fields>;
}

# EasySharedPreferences
-keep class * implements com.haoge.easyandroid.easy.PreferenceSupport { *; }
```