package com.transaction.testController;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.controller.TransactionController;
import com.transaction.entity.Transaction;
import com.transaction.service.KafkaService;
import com.transaction.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TransactionService transactionService;

	@Mock
	private KafkaService kafkaService;

	@InjectMocks
	private TransactionController transactionController;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
	}

	@Test
	public void testAddNewTransaction() throws Exception {
		// Given: Input Transaction
		Transaction inputTransaction = new Transaction(4L, 5021.0, "Bill Payment", null);

		// Given: Expected Output Transaction
		Transaction savedTransaction = new Transaction(4L, 5021.0, "Bill Payment", LocalDateTime.now());
		savedTransaction.setId(17L);

		// Mock service behavior
		when(transactionService.addNewTransaction(any(Transaction.class))).thenReturn(savedTransaction);

		// Fix: Ensure mock handles void method correctly
		doAnswer(invocation -> null).when(kafkaService).addTransaction(any(Transaction.class));

		// Perform POST request
		mockMvc.perform(post("/transaction/addTransaction").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(inputTransaction))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(17)).andExpect(jsonPath("$.userId").value(4))
				.andExpect(jsonPath("$.amount").value(5021.0))
				.andExpect(jsonPath("$.transactionType").value("Bill Payment"))
				.andExpect(jsonPath("$.transactionDate").exists());
	}

	@Test
	public void testGetTransactionById() throws Exception {
		// Given: Mocked transaction
		Transaction transaction = new Transaction(4L, 5021.0, "Bill Payment", LocalDateTime.now());
		transaction.setId(17L);

		// Mocking service behavior
		when(transactionService.getTransactionById(17L)).thenReturn(transaction);

		// Perform GET request
		mockMvc.perform(get("/transaction/17")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(17))
				.andExpect(jsonPath("$.userId").value(4)).andExpect(jsonPath("$.amount").value(5021.0))
				.andExpect(jsonPath("$.transactionType").value("Bill Payment"))
				.andExpect(jsonPath("$.transactionDate").exists());
	}

	@Test
    public void testUpdateTransaction() throws Exception {
        int amt = 5000;

        // Mock KafkaService method
        when(kafkaService.updateTransaction(anyInt())).thenReturn(true);

        // Perform POST request
        mockMvc.perform(post("/transaction/addAmount/{amt}", amt))
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(content().string(String.valueOf(amt))); // Expect response body as amt

        // Verify that KafkaService.updateTransaction() was called once with the given amt
        verify(kafkaService, times(1)).updateTransaction(amt);
    }
}
