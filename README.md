# tokenless
一行代码降低60%的token量。

## 实现原理
提供一个数据格式或者通信协议，将Markdown格式转换成tokenless格式，从而降低token数量。

现在大模型LLM都是通过API进行调用，传输的数据都是JSON和Markdown格式，这种格式特别适合给人类阅读，但实际的业务场景中，绝大多数数据是通过应用程序的API和大模型交互的，这时候可以去掉特殊格式的字符，可以降低token数量，而不改变原文的意思。

## 目标要求
- 准确性：不能改变原文的意思
- 实用性：简单好用，现有项目引入就可以无痛使用
- 抓住主要矛盾：优先解决最主要的问题，如表格数据等
- 通用性：不同格式可以混和处理


## 实现方式

### 表格类数据处理

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

### JSON格式处理

#### 1. JSON转成类似YAML格式

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
```yaml
user:
  name: 张三
  age: 25
  address:
    city: 北京
    street: 长安街
active: true
```

token变化：原始约45 tokens → 转换后约25 tokens，节省44%

简化版YAML格式

在标准YAML基础上进一步简化：去掉冒号后的空格，缩进改成一个空格。

原始YAML：
```yaml
user:
  name: 张三
  age: 25
  address:
    city: 北京
    street: 长安街
active: true
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

token变化：原始约25 tokens → 转换后约18 tokens，节省28%

与标准YAML的区别：

| 对比项 | 标准YAML | tokenless |
|--------|----------|-----------|
| 键值分隔 | `: `（冒号+空格） | `:`（仅冒号） |
| 缩进 | 2空格 | 1空格 |
| 布尔值 | true/false | 1/0 |
| 对象标识 | `key:` 后换行 | `key` 后换行 |


嵌套数组处理：

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




### Markdown格式处理

#### 1. 去掉所有的标题格式

原始：
```markdown
# 一级标题
## 二级标题
### 三级标题
```

转换后：
```
一级标题
二级标题
三级标题
```

token变化：原始约12 tokens → 转换后约6 tokens，节省50%

#### 2. 去掉加粗和斜体标识

原始：
```markdown
这是**重点内容**，还有*斜体文字*和***粗斜体***
```

转换后：
```
这是重点内容，还有斜体文字和粗斜体
```

token变化：原始约18 tokens → 转换后约12 tokens，节省33%

#### 3. 分割线用一个换行符替代

原始：
```markdown
上文内容

---

下文内容
```

转换后：
```
上文内容
下文内容
```

token变化：原始约8 tokens → 转换后约5 tokens，节省37%

#### 4. 多个换行转成一个换行，多个空格制表符换成一个空格

原始：
```markdown
第一段内容



第二段内容    有很多    空格
```

转换后：
```
第一段内容
第二段内容 有很多 空格
```

token变化：原始约15 tokens → 转换后约10 tokens，节省33%

#### 5. 表格转成CSV格式

原始：
```markdown
| 姓名 | 年龄 | 城市 |
|------|------|------|
| 张三 | 25   | 北京 |
| 李四 | 30   | 上海 |
```

转换后：
```
姓名,年龄,城市
张三,25,北京
李四,30,上海
```

token变化：原始约35 tokens → 转换后约15 tokens，节省57%

## 预期效果

- JSON格式：压缩率约 50-60%
- Markdown格式：压缩率约 30-50%

## SDK

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
