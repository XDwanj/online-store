# 网上商城

> 基于 springboot kotlin 的简单网上商城项目

## 技术栈

1. springboot v2.7.3
2. kotlin v1.6.21
3. logback + SLF4J 日志系统
4. Maven 构建工具
5. Swagger3 API文档(springDoc v1.6.9)
6. 使用 WangEditor 富文本编辑器
7. 借入 Alipay 接口

## 架构

1. MVC架构
2. 利用拦截器权限控制
3. 全局异常处理

## 计划

> 未实现

1. MapStruct 作为模型的映射器实现

## 约定

### 响应码

| Code | Msg              | Desc                 |
| ---- | ---------------- | -------------------- |
| 0    | SUCCESS          | 操作成功，无业务异常 |
| 1    | ERROR            | 出现错误             |
| 10   | NEED_LOGIN       | 尚未登录             |
| 2    | ILLEGAL_ARGUMENT | 传入参数非法         |

### 用户权限

| Code | Desc     |
| :--- | :------- |
| 0    | 管理员   |
| 1    | 普通用户 |

### 商品状态

| Code | Desc |
| :--- | :--- |
| 1    | 在售 |
| 2    | 下架 |
| 3    | 删除 |

### 商品类别

| parent_id | Desc                                     |
| --------- | ---------------------------------------- |
| 0         | 表示是分类的根节点，如：数码3C、酒水饮料 |
| 1xxxxx    | 表示是 1xxxxx 的子分类                   |

### 支付平台

| Code | Desc   |
| ---- | ------ |
| 1    | 支付宝 |
| 2    | 微信   |

### 订单状态

| Code | Desc     |
| ---- | -------- |
| 0    | 已取消   |
| 10   | 未支付   |
| 20   | 已付款   |
| 40   | 已发货   |
| 50   | 订单完成 |
| 60   | 订单关闭 |

### 支付状态

| Code | Desc     |
| ---- | -------- |
| 1    | 在线支付 |

### 是否选中

> 购物车项是否被选中之类的

| Code | Desc   |
| ---- | ------ |
| 1    | 选中   |
| 0    | 未选中 |

### 排序规则

| Code | Desc |
| ---- | ---- |
| asc  | 升序 |
| desc | 降序 |
