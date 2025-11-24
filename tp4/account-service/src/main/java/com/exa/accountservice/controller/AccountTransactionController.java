package com.exa.accountservice.controller;

import com.exa.accountservice.dto.AccountTransactionDTO;
import com.exa.accountservice.dto.ApiErrorDTO;
import com.exa.accountservice.dto.TransactionDTO;
import com.exa.accountservice.service.AccountTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/accounts/transactions")
@Tag(name = "Account Transaction Controller", description = "APIs for managing account transactions")
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    public AccountTransactionController(AccountTransactionService accountTransactionService) {
        this.accountTransactionService = accountTransactionService;
    }

    @PostMapping("/topup")
    @Operation(summary = "Top up an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account topped up successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransactionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class))),
    })
    public ResponseEntity<AccountTransactionDTO> topupAccount(@RequestBody TransactionDTO transactionDTO) throws AccountNotFoundException {
        AccountTransactionDTO accountTransactionDTO = accountTransactionService.topupAccount(transactionDTO);
        return ResponseEntity.ok().body(accountTransactionDTO);
    }

    @PostMapping("/charge")
    @Operation(summary = "Charge an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account charged successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransactionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class))),
    })
    public ResponseEntity<AccountTransactionDTO> chargeAccount(@RequestBody TransactionDTO transactionDTO) throws AccountNotFoundException {
        AccountTransactionDTO accountTransactionDTO = accountTransactionService.chargeAccount(transactionDTO);
        return ResponseEntity.ok().body(accountTransactionDTO);
    }

    @GetMapping
    @Operation(summary = "Get account transactions within a date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransactionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class))),
    })
    public ResponseEntity<List<AccountTransactionDTO>> getTransactions(
            @Parameter(description = "Start date in YYYY-MM-DD format")
            @RequestParam
            LocalDate dateAfter,
            @Parameter(description = "Start date in YYYY-MM-DD format")
            @RequestParam
            LocalDate dateBefore) {
        List<AccountTransactionDTO> transactions = accountTransactionService.findAccountTransactionsByDateTimeBetween(dateAfter, dateBefore);
        return ResponseEntity.ok().body(transactions);
    }
}
