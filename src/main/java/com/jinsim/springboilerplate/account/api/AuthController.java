package com.jinsim.springboilerplate.account.api;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.dto.LoginReqDto;
import com.jinsim.springboilerplate.account.dto.LoginResDto;
import com.jinsim.springboilerplate.account.dto.MyAccountResDto;
import com.jinsim.springboilerplate.account.dto.SignupReqDto;
import com.jinsim.springboilerplate.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MyAccountResDto signup(@RequestBody @Valid final SignupReqDto requestDto) {
        Long accountId = accountService.signup(requestDto);
        Account findAccount = accountService.findById(accountId);
        return new MyAccountResDto(findAccount);
    }

    @PostMapping("/login")
    public LoginResDto login(@RequestBody LoginReqDto requestDto) {
        LoginResDto responseDto = accountService.login(requestDto);
        return responseDto;
    }
}
