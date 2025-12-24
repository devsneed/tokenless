package io.github.tokenless;

import com.google.gson.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tokenless Java SDK 测试
 */
public class TokenlessTest {
    
    private static JsonArray testCases;
    
    @BeforeAll
    static void loadTestCases() throws IOException {
        // 加载测试用例
        String content = Files.readString(Path.of("../test-cases.json"));
        JsonObject root = JsonParser.parseString(content).getAsJsonObject();
        testCases = root.getAsJsonArray("testCases");
    }
    
    @Test
    void testTableData() {
        runTestCase(0); // 表格类数据处理
    }
    
    @Test
    void testSimpleJson() {
        runTestCase(1); // 简单JSON对象
    }
    
    @Test
    void testNestedArray() {
        runTestCase(2); // 嵌套数组处理
    }
    
    @Test
    void testMarkdownHeading() {
        runTestCase(3); // Markdown标题处理
    }
    
    @Test
    void testMarkdownBoldItalic() {
        runTestCase(4); // Markdown加粗斜体处理
    }
    
    @Test
    void testMarkdownHr() {
        runTestCase(5); // Markdown分割线处理
    }
    
    @Test
    void testMarkdownWhitespace() {
        runTestCase(6); // Markdown多余空白处理
    }
    
    @Test
    void testMarkdownTable() {
        runTestCase(7); // Markdown表格处理
    }
    
    private void runTestCase(int index) {
        JsonObject tc = testCases.get(index).getAsJsonObject();
        String name = tc.get("name").getAsString();
        JsonElement input = tc.get("input");
        String expected = tc.get("expected").getAsString();
        
        String result;
        if (input.isJsonPrimitive()) {
            result = Tokenless.tokenless(input.getAsString());
        } else {
            result = Tokenless.tokenless(input.toString());
        }
        
        assertEquals(expected, result, "测试失败: " + name);
    }
}
