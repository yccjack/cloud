## entrance说明
## 约定

### 头部信息

**所有的头部必须包括一下信息**

```properties
Accept-Encoding: gzip, deflate
authorize-token: ***
Connection: keep-alive
Content-Type: application/json
```

****

### 调用

脚标内容~()内为web调用形式，[]为内部/feign调用枚举类。



### 错误类型

一些非系统数据，丢失数据，参数缺失，等错误会返回一系列的错误码;

错误编码：

```java
public enum CommonResultEnum implements ICommonEnum {
    SUCCESS("200", "成功"),
    FAILED("40000", "失败"),
    FAILED_MISSING_PARAMS("40001", "失败，参数缺失"),
    FAILED_INSUFFICIENT_AUTHORITY("40002", "失败，权限不足"),
    FAILED_404("40004", "失败,资源丢失"),
    SYSTEM_FAIL("99999", "系统错误");

    private String code;
    private String msg;

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    private CommonResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
```

返回错误示例：

```json
{
    "code": "40004",
    "msg": "失败,资源丢失",
    "data": null
}
```

### Demo相关

#### 请求地址


目前内置了3种请求类型：la,lu,au 
http://host:port/demo/la开头；
http://host:port/demo/lu开头；
http://host:port/demo/au开头；

#### 操作类型

**操作紧跟在后面，比如：   http://host:port/demo/la/create**

#### 事件：

所有请求以post请求发送，需要携带json数据，数据格式：

```json
{"event":"submit","data":{}}
```

event事件为页面操作事件，比如提交事件，则event=submit，保存事件，则event=save，以此类推！

---

### 总结
以某种类型的请求和此种类型需要操作的方式以及发生的事件为走向 实现的通用接口。
请求地址示例： 
```html
post:  
http://localhost:8081/entrance/demo/lu/create/     
{"event":"submit","data":{"data":"1234"}}
```