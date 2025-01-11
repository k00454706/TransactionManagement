package org.hometask.transactionmanagement.performace;

import org.hometask.transactionmanagement.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionApiLoadTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final String baseUrl = "http://localhost:";

    @Test
    void loadTest_CreateTransaction() throws InterruptedException {
        int numberOfThreads = 100;
        int requestsPerThread = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfThreads; i++) {
            executor.execute(() -> {
                try {
                    for (int j = 0; j < requestsPerThread; j++) {
                        Transaction transaction = new Transaction();
                        transaction.setTransactionNumber("TEST-" + System.currentTimeMillis());
                        transaction.setAmount(BigDecimal.valueOf(100.0));

                        ResponseEntity<Transaction> response = restTemplate.postForEntity(
                                baseUrl + port + "/api/v1/transaction",
                                transaction,
                                Transaction.class);

                        if (response.getStatusCode().is2xxSuccessful()) {
                            successCount.incrementAndGet();
                        } else {
                            failCount.incrementAndGet();
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        boolean completed = latch.await(2, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();
        executor.shutdown();

        // 输出测试结果
        System.out.println("Load Test Results:");
        System.out.println("Total Requests: " + (numberOfThreads * requestsPerThread));
        System.out.println("Successful Requests: " + successCount.get());
        System.out.println("Failed Requests: " + failCount.get());
        System.out.println("Total Time (ms): " + (endTime - startTime));
        System.out.println("Average Time per Request (ms): " +
                ((endTime - startTime) / (float)(numberOfThreads * requestsPerThread)));

        assertTrue(completed, "Load test did not complete in time");
        assertTrue(successCount.get() > (numberOfThreads * requestsPerThread * 0.95),
                "Success rate should be above 95%");
    }
}