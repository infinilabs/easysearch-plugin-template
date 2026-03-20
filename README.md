# Easysearch Plugin Template

一个极简的 Easysearch 插件开发模板，演示如何实现自定义 REST API 端点。

## 快速开始

### 1. 使用模板创建你的插件

点击 GitHub 的 "Use this template" 按钮，或手动克隆后修改：

```bash
# 修改插件名称（3个地方）
# 1. build.gradle: esplugin.name = 'your-plugin-name'
# 2. settings.gradle: rootProject.name = 'your-project-name'
# 3. 包名: src/main/java/org/easysearch/plugin/yourname/
```

### 2. 构建插件

```bash
./gradlew build
```

插件包将生成在 `build/distributions/your-plugin.zip`

### 版本适配说明

如果你要适配不同的 Easysearch 版本，请修改 `build.gradle` 中这一行：

```groovy
easysearch_version = System.getProperty("easysearch.version", "2.1.0")
```

将默认版本号 `2.1.0` 改为目标版本，或在构建时通过 `-Deasysearch.version=目标版本` 覆盖。

示例命令（可直接复制）：

```bash
./gradlew build -Deasysearch.version=2.2.0
```

### 3. 安装到 Easysearch

```bash
# 在 Easysearch 目录中执行
bin/easysearch-plugin install file:///path/to/your-plugin.zip
```

## 模板示例功能

本模板实现了一个简单的 `/_hello` REST 端点：

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

## 开发你自己的插件

### 项目结构

```
src/main/java/org/easysearch/plugin/hello/
├── HelloPlugin.java           # 插件入口类
└── rest/
    └── RestHelloAction.java   # REST 处理器示例
```

### 关键步骤

1. **修改插件类**
   - 重命名 `HelloPlugin.java` 为你自己的类名
   - 实现需要的插件接口（如 `ActionPlugin`, `AnalysisPlugin` 等）

2. **添加 REST 端点**
   - 参考 `RestHelloAction.java` 创建新的 REST 处理器
   - 在 `HelloPlugin.getRestHandlers()` 中注册

3. **配置插件信息**
   ```gradle
   esplugin {
       name 'your-plugin-name'                    # 插件标识名
       description 'Your plugin description'      # 插件描述
       classname 'org.easysearch.plugin.your.YourPlugin'  # 主类全名
   }
   ```

## 版本兼容

| 组件 | 版本 |
|------|------|
| Easysearch | 2.x |
| Gradle | 8.10.2 |
| Java | 11+ |

## 许可证

Copyright © INFINI Ltd. All rights reserved.  
Licensed under the [Apache License, Version 2.0](LICENSE.txt).

Easysearch is a trademark of [INFINI Ltd](https://infinilabs.com).
