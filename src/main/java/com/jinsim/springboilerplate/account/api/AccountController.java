package com.jinsim.springboilerplate.account.api;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.dto.*;
import com.jinsim.springboilerplate.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequestMapping("/accounts")
@RequiredArgsConstructor // final이 붙은 필드에 대해 생성자를 만들어준다.
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MyAccountResDto signup(@RequestBody @Valid final SignupReqDto requestDto) {
        Long accountId = accountService.signup(requestDto);
        Account findAccount = accountService.findById(accountId);
        return new MyAccountResDto(findAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto requestDto) {
        LoginResDto responseDto = accountService.login(requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public MyAccountResDto getMyAccount(@PathVariable final Long id) {
        Account account = accountService.findById(id);
        return new MyAccountResDto(account);
    }

    @PutMapping("{id}")
    public MyAccountResDto updateMyAccount(@PathVariable final Long id,
                                           @RequestBody final UpdateAccountReqDto requestDto) {
        accountService.update(id, requestDto);
        Account account = accountService.findById(id);
        return new MyAccountResDto(account);
    }

    @DeleteMapping("/{id}")
    public MyAccountResDto deleteMyAccount(@PathVariable final Long id) {
        accountService.delete(id);
        Account account = accountService.findById(id);
        return new MyAccountResDto(account);
    }
}
