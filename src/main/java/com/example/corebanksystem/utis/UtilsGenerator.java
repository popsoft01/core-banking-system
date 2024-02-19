package com.example.corebanksystem.utis;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UtilsGenerator {
    private static final String BANK_CODE = "023"; // Sample bank code
    private static final AtomicLong counter = new AtomicLong(1000000000L); // Initial account number

    private static final String PREFIX = "TXN"; // Static prefix for transaction reference code

    public static String generateAccountNumber(){

            // Generate a unique numeric identifier
            long uniqueId = counter.getAndIncrement();

            // Format the account number with the bank code and the unique identifier
            String accountNumber = String.format("%s%010d", BANK_CODE, uniqueId);


            return accountNumber;
    }

    public static String generateTransactionRefCode() {
        // Generate a unique identifier using UUID
        String uniqueId = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

        // Get the current timestamp
        LocalDateTime now = LocalDateTime.now();

        // Format the current timestamp
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // Concatenate the prefix, timestamp, and unique identifier to form the transaction reference code
        String transactionRefCode = PREFIX + timestamp + uniqueId;

        return transactionRefCode;
    }
}
