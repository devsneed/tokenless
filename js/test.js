/**
 * tokenless JavaScript SDK 测试
 */

import { tokenless, convertJson, convertMarkdown } from './index.js';
import { readFileSync } from 'fs';

// 加载测试用例
const testCases = JSON.parse(readFileSync('../test-cases.json', 'utf-8')).testCases;

let passed = 0;
let failed = 0;

console.log('=== tokenless JavaScript SDK 测试 ===\n');

for (const tc of testCases) {
  const result = tokenless(tc.input);
  const success = result === tc.expected;
  
  if (success) {
    console.log(`✓ ${tc.name}`);
    passed++;
  } else {
    console.log(`✗ ${tc.name}`);
    console.log(`  期望: ${JSON.stringify(tc.expected)}`);
    console.log(`  实际: ${JSON.stringify(result)}`);
    failed++;
  }
}

console.log(`\n总计: ${passed} 通过, ${failed} 失败`);
process.exit(failed > 0 ? 1 : 0);
