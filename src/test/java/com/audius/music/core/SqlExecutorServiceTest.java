package com.audius.music.core;

import com.audius.music.core.utils.SqlExecutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SqlExecutorServiceTest {

    @Autowired
    private SqlExecutorService sqlExecutorService;

    @Test
    void testExecuteSelectSingle() {


        // Act: SELECT query
        Object result = sqlExecutorService.executeSelectSingle("""
            SELECT value
            FROM config.credentials;
        """);

        // Assert
        assertNotNull(result);
        assertEquals("hello", result.toString());
    }
}
