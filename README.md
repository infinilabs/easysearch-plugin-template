# Easysearch Plugin Template

一个极简的 Easysearch 插件开发模板，演示如何实现自定义 REST API 端点。

## 你会得到什么

- 一个可用的 Easysearch 插件基础项目（含插件入口、REST 示例和测试）
- 一个方便改造的 REST 端点示例：`GET /_hello?name=tom`
- 提供两种构建方式：`Gradle`（主构建）和 `Maven`（参考示例）

## 构建方式（建议先看）

- **Gradle（主构建）**：用于日常开发和完整验证，包含编译、打包、单元测试、`integTest`、`yamlRestTest`
- **Maven（示例构建）**：提供编译、单元测试和基础 zip 打包，适合习惯 Maven 的团队参考

常用命令：

```bash
./gradlew build
./gradlew run
mvn package
```

产物位置：

- `Gradle`：`build/distributions/your-plugin.zip`
- `Maven`：`target/your-plugin.zip`

## 快速开始

### 1) 使用模板并改名

创建仓库后，建议至少同步修改以下位置：

```bash
# 1. build.gradle: esplugin.name = 'your-plugin-name'
# 2. settings.gradle: rootProject.name = 'your-project-name'
# 3. 包名: src/main/java/org/easysearch/plugin/yourname/
```

### 2) 本地构建

```bash
# 推荐（主构建）
./gradlew build

# 可选（Maven 示例构建）
mvn package
```

### 3) 安装到 Easysearch

```bash
# 在 Easysearch 安装目录执行
bin/easysearch-plugin install file:///path/to/your-plugin.zip
```

## Maven 示例说明

Maven 相关辅助文件统一放在 `maven/` 下，`pom.xml` 保持在项目根目录（符合 Maven 工程习惯）。

- `maven/plugin-metadata/plugin-descriptor.properties`：Maven 构建使用的 descriptor 模板
- `maven/assembly/plugin.xml`：Maven 打包结构定义（仅 Maven 使用）

说明：

- `plugin.xml` 仅被 `maven-assembly-plugin` 读取，用于定义 zip 内文件布局
- Gradle 不读取 `plugin.xml`，插件 descriptor 由 `easysearch.esplugin` 在构建阶段自动生成

## 版本适配

如果你要适配不同的 Easysearch 版本：

- `Gradle`：修改 `build.gradle` 的默认值，或通过系统属性覆盖
- `Maven`：修改 `pom.xml` 中的 `easysearch.version`

`Gradle` 默认值示例：

```groovy
easysearch_version = System.getProperty("easysearch.version", "2.1.1")
```

示例命令（可直接复制）：

```bash
./gradlew build -Deasysearch.version=2.2.0
mvn package -Deasysearch.version=2.2.0
```

## 示例功能

请求：

```bash
GET /_hello?name=tom
```

响应：

```json
{
  "message": "Hello tom",
  "cluster_name": "easysearch-cluster",
  "node_name": "node-1",
  "timestamp": 1710000000000
}
```

## 开发指南

### 目录结构（核心）

```text
src/main/java/org/easysearch/plugin/hello/
├── HelloPlugin.java
└── rest/
    └── RestHelloAction.java
```

### 改造步骤

1. 重命名插件入口类（`HelloPlugin.java`），并按需实现插件接口
2. 参考 `RestHelloAction.java` 新增 REST 处理器
3. 在 `HelloPlugin.getRestHandlers()` 中完成处理器注册
4. 更新插件元数据与构建配置
   - `Gradle`：更新 `build.gradle` 的 `esplugin { ... }`
   - `Maven`：更新 `pom.xml`（及 Maven 模板相关字段）

## 测试

模板当前包含两类基础测试：

- 单元测试：`src/test/java/org/easysearch/plugin/hello/rest/RestHelloActionTest.java`
- YAML REST suite：`src/yamlRestTest/java/org/easysearch/plugin/hello/HelloClientYamlTestSuiteIT.java`
- API 定义：`src/yamlRestTest/resources/rest-api-spec/api/hello.json`
- YAML 用例：`src/yamlRestTest/resources/rest-api-spec/test/hello/10_basic.yml`

常用测试命令：

```bash
./gradlew test
./gradlew integTest
./gradlew yamlRestTest
./gradlew check
mvn test
```

说明：

- `./gradlew integTest`：运行基于测试集群的插件集成测试（比单元测试更接近真实运行环境）
- `./gradlew yamlRestTest`：运行 YAML REST 测试套件
- `./gradlew check`：聚合执行校验任务（包含 `test`、`integTest` 与 `yamlRestTest`）
- `mvn test`：仅覆盖编译与单元测试，不含 `integTest` 与 `yamlRestTest`，不替代 Gradle 的完整校验链路

## 本地运行（Gradle）

如果你想在本地启动一个带当前插件的测试节点进行调试，可使用：

```bash
./gradlew run
```

常见用途：

- 本地联调 REST 接口（如 `/_hello`）
- 调试插件启动过程与日志输出
- 快速验证配置修改是否生效

### `run` 启动后如何访问节点

`run` 任务会启动一个本地测试节点。启动成功后，可按以下方式访问：

1. 查看 `run` 任务终端输出中的 HTTP 地址（通常会显示监听地址与端口）。
2. 使用 `curl` 请求插件接口（端口以终端输出为准，常见为 `9200`）：

```bash
curl -s "http://127.0.0.1:9200/_hello?name=tom"
```

如果返回包含 `message`、`cluster_name`、`node_name` 等字段，说明插件已成功加载并可访问。

### `run` 启动后如何查看日志

可以通过以下两种方式查看日志：

1. 直接看 `./gradlew run` 的控制台输出。
2. 查看测试集群日志目录下的日志文件（`runTask` 对应目录）：

```bash
ls build/testclusters/runTask-0/logs
tail -f build/testclusters/runTask-0/logs/*.log
```

如果你的本地环境生成了不同编号目录（例如 `runTask-1`），请以 `build/testclusters/` 下实际目录为准。

## 版本兼容

| 组件 | 版本 | 说明 |
|------|------|------|
| Easysearch | 2.x | |
| Gradle | 8.10.2 | 由 Gradle Wrapper 锁定，建议使用 `./gradlew` 而非本地 Gradle |
| Maven | 3.6.3+ | 由 `maven-compiler-plugin 3.14.0` 决定最低版本要求 |
| Java | 11+ | |

## 许可证

Copyright © INFINI LTD. All rights reserved.  
Licensed under the [Apache License, Version 2.0](LICENSE.txt).
