"""
tokenless Python SDK 测试
"""

import json
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent.parent))
from tokenless import tokenless, convert_json, convert_markdown

# 加载测试用例
test_cases_path = Path(__file__).parent.parent.parent / 'test-cases.json'
with open(test_cases_path, 'r', encoding='utf-8') as f:
    test_cases = json.load(f)['testCases']


def test_all_cases():
    """运行所有测试用例"""
    passed = 0
    failed = 0
    
    print('=== tokenless Python SDK 测试 ===\n')
    
    for tc in test_cases:
        result = tokenless(tc['input'])
        expected = tc['expected']
        success = result == expected
        
        if success:
            print(f"✓ {tc['name']}")
            passed += 1
        else:
            print(f"✗ {tc['name']}")
            print(f"  期望: {repr(expected)}")
            print(f"  实际: {repr(result)}")
            failed += 1
    
    print(f"\n总计: {passed} 通过, {failed} 失败")
    return failed == 0


if __name__ == '__main__':
    success = test_all_cases()
    sys.exit(0 if success else 1)
