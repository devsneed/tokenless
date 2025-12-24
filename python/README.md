# tokenless Python SDK

一行代码降低60%的token量。

## 安装

```bash
pip install tokenless
```

## 使用方法

```python
from tokenless import tokenless

# 自动检测输入类型并转换
result = tokenless(data)
```

## API

### tokenless(input_data)

主入口函数，自动检测输入类型并转换。

- 输入JSON对象/数组时，转换为tokenless格式
- 输入Markdown字符串时，去除冗余格式

```python
# JSON对象数组 -> CSV格式
users = [
    {'name': '张三', 'age': 25, 'city': '北京'},
    {'name': '李四', 'age': 30, 'city': '上海'}
]
tokenless(users)
# 输出:
# name,age,city
# 张三,25,北京
# 李四,30,上海

# 嵌套JSON -> 简化YAML格式
data = {
    'user': {'name': '张三', 'age': 25},
    'active': True
}
tokenless(data)
# 输出:
# user
#  name:张三
#  age:25
# active:1

# Markdown -> 纯文本
tokenless('# 标题\n**重点**内容')
# 输出: 标题\n重点内容
```

### convert_json(data, indent=0)

仅转换JSON数据。

### convert_markdown(text)

仅转换Markdown文本。

## 运行测试

```bash
python tests/test_tokenless.py
```
