package com.jinsim.springboilerplate.domain.account.api;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.account.dto.SignUpReqDto;
import com.jinsim.springboilerplate.domain.account.service.AccountService;
import com.jinsim.springboilerplate.domain.account.dto.MyAccountResDto;
import com.jinsim.springboilerplate.domain.account.dto.UpdateAccountReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Account API", description = "계정 API")
@Slf4j
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "회원가입", description = "계정을 생성합니다.")
    @Parameters({
            @Parameter(name = "requestDto", description = "회원가입 요청 객체"),
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MyAccountResDto signUp(@RequestBody @Valid final SignUpReqDto requestDto) {
        Long accountId = accountService.signUp(requestDto);
        Account findAccount = accountService.findById(accountId);
        return new MyAccountResDto(findAccount);
    }


    @Operation(summary = "회원조회", description = "계정 정보를 조회합니다.")
    @Parameters({
            @Parameter(name = "accountId", description = "계정 아이디", example = "1")
    })
    @GetMapping("/{accountId}")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#accountId).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    public MyAccountResDto getMyAccount(@PathVariable final Long accountId) {
        Account account = accountService.findById(accountId);
        return new MyAccountResDto(account);
    }

    @Operation(summary = "회원수정", description = "계정 정보를 수정합니다.")
    @Parameters({
            @Parameter(name = "accountId", description = "계정 아이디", example = "1"),
            @Parameter(name = "requestDto", description = "회원수정 요청 객체")
    })
    @PutMapping("/{accountId}")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#accountId).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    public MyAccountResDto updateMyAccount(@PathVariable final Long accountId,
                                           @RequestBody final UpdateAccountReqDto requestDto) {
        accountService.update(accountId, requestDto);
        Account account = accountService.findById(accountId);
        return new MyAccountResDto(account);
    }

    @Operation(summary = "회원삭제", description = "계정을 삭제합니다.")
    @Parameters({
            @Parameter(name = "accountId", description = "계정 아이디", example = "1")
    })
    @DeleteMapping("/{accountId}")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#accountId).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteMyAccount(@PathVariable final Long accountId) {
        accountService.delete(accountId);
    }
}
