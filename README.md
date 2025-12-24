# tokenless

[English](./README_EN.md) | 中文

一行代码降低60%的token量。

## 1. 实现原理

提供一个数据格式或者通信协议，将Markdown格式转换成tokenless格式，从而降低token数量。

现在大模型LLM都是通过API进行调用，传输的数据都是JSON和Markdown格式，这种格式特别适合给人类阅读，但实际的业务场景中，绝大多数数据是通过应用程序的API和大模型交互的，这时候可以去掉特殊格式的字符，可以降低token数量，而不改变原文的意思。

## 2. 设计目标

- 准确性：不能改变原文的意思
- 实用性：简单好用，现有项目引入就可以无痛使用
- 抓住主要矛盾：优先解决最主要的问题，如表格数据等
- 通用性：不同格式可以混和处理

## 3. 转换规则

### 3.1 表格类数据处理

将对象数组转成类似CSV格式，首行为表头。

原始：
```json
[
  {"name": "张三", "age": 25, "city": "北京"},
  {"name": "李四", "age": 30, "city": "上海"},
  {"name": "王五", "age": 28, "city": "广州"}
]
```

转换后：
```
name,age,city
张三,25,北京
李四,30,上海
王五,28,广州
```

token变化：原始约55 tokens → 转换后约18 tokens，节省67%

### 3.2 JSON格式处理

将JSON转成简化版YAML格式：去掉冒号后的空格，缩进改成一个空格。

原始：
```json
{
  "user": {
    "name": "张三",
    "age": 25,
    "address": {
      "city": "北京",
      "street": "长安街"
    }
  },
  "active": true
}
```

转换后：
```
user
 name:张三
 age:25
 address
  city:北京
  street:长安街
active:1
```

token变化：原始约45 tokens → 转换后约18 tokens，节省60%

与标准YAML的区别：

| 对比项 | 标准YAML | tokenless |
|--------|----------|-----------|
| 键值分隔 | `: `（冒号+空格） | `:`（仅冒号） |
| 缩进 | 2空格 | 1空格 |
| 布尔值 | true/false | 1/0 |
| 对象标识 | `key:` 后换行 | `key` 后换行 |

### 3.3 嵌套数组处理

原始：
```json
{
  "departments": [
    {
      "name": "研发部",
      "employees": [
        {"name": "张三", "role": "前端"},
        {"name": "李四", "role": "后端"}
      ]
    }
  ]
}
```

转换后：
```
departments
 -
  name:研发部
  employees
   name,role
   张三,前端
   李四,后端
```

token变化：原始约50 tokens → 转换后约20 tokens，节省60%

说明：用 `-` 表示数组元素，叶子层的对象数组转CSV格式。

### 3.4 Markdown格式处理

| 处理项 | 原始 | 转换后 | 节省 |
|--------|------|--------|------|
| 标题格式 | `# 标题` | `标题` | 50% |
| 加粗斜体 | `**重点**` | `重点` | 33% |
| 分割线 | `---` | 换行符 | 37% |
| 多余空白 | 多个换行/空格 | 单个 | 33% |
| 表格 | Markdown表格 | CSV格式 | 57% |

## 4. 预期效果

- JSON格式：压缩率约 50-60%
- Markdown格式：压缩率约 30-50%

## 5. SDK

提供三种语言的SDK实现：

| 语言 | 目录 | 安装命令 |
|------|------|----------|
| JavaScript | [js/](./js/) | `npm install tokenless` |
| Python | [python/](./python/) | `pip install tokenless` |
| Java | [java/](./java/) | Maven依赖 |

### 快速使用

JavaScript:
```javascript
import { tokenless } from 'tokenless';
const result = tokenless(data);
```

Python:
```python
from tokenless import tokenless
result = tokenless(data)
```

Java:
```java
import io.github.tokenless.Tokenless;
String result = Tokenless.tokenless(data);
```

详细使用说明请查看各SDK目录下的README文档。

## 6. 许可证

[MIT](./LICENSE)
