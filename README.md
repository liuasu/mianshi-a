# 六啊朔面试刷题网

> 作者：[程序员六啊朔](https://github.com/liuasu)
> 仅分享于 [GitHub](https://github.com/liuasu)

基于“面试鸭”的面试刷题网。

### 后端技术

基于Spring Boot + Redis + MySQL + Elasticsearch + Next.js 服务端渲染的面试刷题平台。


## 业务功能

- 刷题记录：基于 Redis BitMap + Redisson 实现用户年度刷题记录的统计，相比数据库存储节约几百倍空间。并通过本地缓存 + 返回值优化 + 位运算进一步提升接口性能。
- 分词搜索：自主搭建 ES 代替 MySQL 模糊查询，并通过为索引绑定 ik 分词器实现了更灵活的分词搜索。
- 基于 MyBatis 的 batch 操作实现题目批量管理，并通过任务拆分 + CompletableFuture 并发编程提升批处理性能。
- 引入 Druid 连接池来监控慢 SQL，并通过调整连接数配置，进一步提升了批量操作的性能。
- 使用 Caffeine 本地缓存提升题库查询性能，并通过接入 Hotkey 并配置热 key 探测规则来自动缓存热门题目，防止瞬时流量击垮数据库。
- 为限制恶意用户访问，基于 WebFilter + BloomFilter 实现 IP 黑名单拦截，并通过 Nacos 配置中心动态更新黑名单，便于维护。
- 使用 Knife4j + Swagger 自动生成后端接口文档，并通过编写 ApiOperation 等注解补充接口注释，避免了人工编写维护文档的麻烦。

