# tokenless

English | [中文](./README.md)

Reduce 60% token usage with one line of code.

## 1. How It Works

Provides a data format/protocol to convert JSON and Markdown into tokenless format, reducing token count.

LLMs are called via APIs using JSON and Markdown formats. These formats are human-readable, but in most business scenarios, data is exchanged between applications and LLMs programmatically. By removing formatting characters, we can reduce token count without changing the meaning.

## 2. Design Goals

- Accuracy: Preserve original meaning
- Usability: Easy integration with existing projects
- Focus: Prioritize high-impact optimizations (e.g., tabular data)
- Versatility: Handle mixed formats

## 3. Conversion Rules

### 3.1 Array of Objects → CSV

Original:
```json
[
  {"name": "Alice", "age": 25, "city": "Beijing"},
  {"name": "Bob", "age": 30, "city": "Shanghai"}
]
```

Converted:
```
name,age,city
Alice,25,Beijing
Bob,30,Shanghai
```

Token reduction: ~67%

### 3.2 JSON → Simplified YAML

Convert JSON to simplified YAML: no space after colon, single space indentation.

Original:
```json
{
  "user": {
    "name": "Alice",
    "age": 25,
    "address": {
      "city": "Beijing",
      "street": "Main St"
    }
  },
  "active": true
}
```

Converted:
```
user
 name:Alice
 age:25
 address
  city:Beijing
  street:Main St
active:1
```

Token reduction: ~60%

Differences from standard YAML:

| Item | Standard YAML | tokenless |
|------|---------------|-----------|
| Key-value separator | `: ` (colon + space) | `:` (colon only) |
| Indentation | 2 spaces | 1 space |
| Boolean | true/false | 1/0 |
| Object marker | `key:` + newline | `key` + newline |

### 3.3 Nested Array Handling

Original:
```json
{
  "departments": [
    {
      "name": "R&D",
      "employees": [
        {"name": "Alice", "role": "Frontend"},
        {"name": "Bob", "role": "Backend"}
      ]
    }
  ]
}
```

Converted:
```
departments
 -
  name:R&D
  employees
   name,role
   Alice,Frontend
   Bob,Backend
```

Token reduction: ~60%

Note: Use `-` for array elements, leaf-level object arrays convert to CSV format.

### 3.4 Markdown Processing

| Item | Original | Converted | Reduction |
|------|----------|-----------|-----------|
| Headings | `# Title` | `Title` | 50% |
| Bold/Italic | `**text**` | `text` | 33% |
| Horizontal rule | `---` | newline | 37% |
| Extra whitespace | multiple | single | 33% |
| Tables | MD table | CSV | 57% |

## 4. Expected Results

- JSON: ~50-60% reduction
- Markdown: ~30-50% reduction

## 5. SDK

Three language SDKs available:

| Language | Directory | Install |
|----------|-----------|---------|
| JavaScript | [js/](./js/) | `npm install tokenless` |
| Python | [python/](./python/) | `pip install tokenless` |
| Java | [java/](./java/) | Maven dependency |

### Quick Start

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

See each SDK directory for detailed documentation.

## 6. License

[MIT](./LICENSE)
