# EasyFormater

EasyFormater用于对任意类型数据进行格式化操作。

[Sample Activity](../app/src/main/java/com/haoge/sample/easyandroid/activities/EasyFormaterActivity.kt)

## 特性

- 支持对多种数据格式进行格式化排版：
> List/Map/Array/JSONObject/JSONArray/POJO
- 行数限制：可限制最高输出行数。便于展示时使用

## 用法

### 直接使用默认样式进行format.

```kotlin
// 创建待格式化数据
val origin = create()
// 直接使用扩展函数format(). 此函数会使用默认提供的实例进行格式化操作
val formatResult = origin.format()
```

### 使用自定义样式进行格式化

直接使用代码说明

```kotlin
// 创建builder实例。并添加配置
val builder = EasyFormater.newBuilder()
builder.maxLines = 50   //(默认为-1)
builder.maxArraySize = 20//(默认为-1)
builder.maxMapSize = 20 //(默认为-1)

// 创建出formater实例并使用
val formater = builder.build()
formater.formatAny(any)// 支持格式化任意类型数据。
```

### 参数说明

#### maxLines:

最大输出行数。当格式化后的行数高于此输出限制，将对超出部门的数据进行平铺。即超出部分不进行换行

比如当maxLines设置为5时，对list = listof(1,2,3,4,5,6)数据进行格式化输出：

```
[
    1,
    2,
    3,
    4,5,6]
```

#### maxArraySize:

Array型数据(Array/List/Set/JSONArray)允许的最大行数，当数据中的此类数据长度超出限制。将对整体数据进行平铺

比如对于一个长度为5的数组数据：array = arrayof(1,2,3,4,5)：

1. 当指定maxArraySize>=5时,输出：

```
[
    1,
    2,
    3,
    4,
    5
]
```

2. 当指定maxArraySize<5时，输出

```
[1,2,3,4,5]
```

#### maxMapSize

对象型数据(Bean/JSONObject/Map)允许的最大行数，行为与maxArraySize类似。

**当限制值为-1时。表示去除限制**

