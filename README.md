



# Cloud

- [x] 短链服务

- [x] api接口签名

- [x] admin监控

- [x] gateway网关

- [x] jwt 认证

- [x] stream

- [ ] 集群部署

- [ ] 高可用注册中心

- [ ] 文件系统

- [ ] 日志系统

- [ ] 邮件系统

  
## 整体结构

![未命名文件](https://gitee.com/MysticalYu/pic/raw/master/hexo/%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6.png)

## 入口逻辑图

![入口架构图 (1)](https://gitee.com/MysticalYu/pic/raw/master/hexo/%E5%85%A5%E5%8F%A3%E6%9E%B6%E6%9E%84%E5%9B%BE%20(1).png)



**邮件服务暂时未在仓库中**

## 服务

### auth-service

支持JWT认证，api签名认证



### entrance-service

入口服务，供业务拓展，详见入口逻辑图







