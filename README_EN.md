# tokenless

Reduce 60% token usage with one line of code.

## How It Works

Provides a data format/protocol to convert JSON and Markdown into tokenless format, reducing token count.

LLMs are called via APIs using JSON and Markdown formats. These formats are human-readable, but in most business scenarios, data is exchanged between applications and LLMs programmatically. By removing formatting characters, we can reduce token count without changing the meaning.

## Goals

- Accuracy: Preserve original meaning
- Usability: Easy integration with existing projects
- Focus: Prioritize high-impact optimizations (e.g., tabular data)
- Versatility: Handle mixed formats

## Conversion Rules

### Array of Objects → CSV

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

### JSON → Simplified YAML

Original:
```json
{
  "user": {
    "name": "Alice",
    "age": 25
  },
  "active": true
}
```

Converted:
```
user
 name:Alice
 age:25
active:1
```

Differences from standard YAML:
- No space after colon
- Single space indentation
- Boolean: 1/0 instead of true/false

Token reduction: ~50-60%

### Markdown Simplification

- Remove heading markers (`#`)
- Remove bold/italic markers (`**`, `*`)
- Replace horizontal rules with newline
- Collapse multiple whitespace
- Convert tables to CSV

Token reduction: ~30-50%

## SDK

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

## License

[MIT](./LICENSE)
