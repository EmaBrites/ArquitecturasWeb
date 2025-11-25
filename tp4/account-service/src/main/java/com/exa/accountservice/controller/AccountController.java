package com.exa.accountservice.controller;

import com.exa.accountservice.dto.AccountDTO;
import com.exa.accountservice.dto.ApiErrorDTO;
import com.exa.accountservice.dto.CreateAccountDTO;
import com.exa.accountservice.dto.UpdateAccountDTO;
import com.exa.accountservice.dto.mp.PaymentResponseDTO;
import com.exa.accountservice.enums.AccountStateEnum;
import com.exa.accountservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.net.URI;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account Controller", description = "APIs for managing accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping//TODO checkear que el usuario sea valido(llamado feign). Probé creando userid 0 sin que exista y funciona
    @Operation(summary = "Create a new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class)))
    })
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
        AccountDTO createdAccount = accountService.createAccount(createAccountDTO);
        URI location = URI.create(String.format("/account/%s", createdAccount.getId()));
        return ResponseEntity.created(location).body(createdAccount);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class))),
    })
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Integer id) throws AccountNotFoundException {
        AccountDTO accountDTO = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class))),
    })
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Integer id, @RequestBody UpdateAccountDTO updateAccountDTO) throws AccountNotFoundException {
        AccountDTO updatedAccount = accountService.updateAccount(id, updateAccountDTO);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class))),
    })
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable Integer id) throws AccountNotFoundException {
        AccountDTO deletedAccount = accountService.deleteAccount(id);
        return ResponseEntity.ok(deletedAccount);
    }

    @PutMapping("/{id}/state")
    @Operation(summary = "Update account state (activate/deactivate)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account state updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class))),
    })
    public ResponseEntity<AccountDTO> updateAccountState(@PathVariable Integer id, @RequestParam AccountStateEnum accountState) throws AccountNotFoundException {
        AccountDTO updatedAccount = accountService.updateAccountState(id, accountState);
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/{id}/charge")
    public ResponseEntity<PaymentResponseDTO> charge(
            @PathVariable Integer id,
            @RequestParam Double amount) {

        PaymentResponseDTO response = accountService.chargeAccount(id, amount);
        return ResponseEntity.ok(response);
    }

}
