
## sql
运行data.sql,导入初始数据
## 用户验证服务

### 使用：
- 登录用户：
 > 开启gateway工程， 请求登录服务登录
  ```html
localhost:11000/sso/login
```
参数【post请求json参数】：
```js
{"username":"ycc","password":"123456"}
```
成功后会自动写入cookie到浏览器，可以放置在head中也可以不用。接下来就可以正常请求别的服务

## 签名服务

### 说明
> 避免请求重放

### 使用
 > 开启gateway工程， 请求获取签名[将数据一并传入]
```html
localhost:11000/sso/signature/base
```
如果有请求体则输入请求体，这个时候会返回我们需要的签名数据，如下：
```json
{
    "appId": "APP_ID_TEST",
    "data": null,
    "timestamp": 1631778261,
    "nonce": 1781794041,
    "signature": "L6RcQFOeh7mOTNwXfnvenPz7ZZk="
}
```
复制这些请求到测试接口测试即可：
测试接口
- localhost:11000/sso/signature/param 以参数形式验证，把base返回的数据复制进json body里面即可
- localhost:11000/sso/signature/head 以请求头验证，把base数据拼接到header中即可