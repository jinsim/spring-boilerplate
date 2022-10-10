package com.jinsim.springboilerplate.account.api;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.dto.*;
import com.jinsim.springboilerplate.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequestMapping("/account")
@RequiredArgsConstructor // final이 붙은 필드에 대해 생성자를 만들어준다.
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MyAccountResDto signUp(@RequestBody @Valid final SignUpReqDto requestDto) {
        Long accountId = accountService.signUp(requestDto);
        Account findAccount = accountService.findById(accountId);
        return new MyAccountResDto(findAccount);
    }


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#id).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    public MyAccountResDto getMyAccount(@PathVariable final Long id) {
        Account account = accountService.findById(id);
        return new MyAccountResDto(account);
    }

    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#id).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    public MyAccountResDto updateMyAccount(@PathVariable final Long id,
                                           @RequestBody final UpdateAccountReqDto requestDto) {
        accountService.update(id, requestDto);
        Account account = accountService.findById(id);
        return new MyAccountResDto(account);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#id).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteMyAccount(@PathVariable final Long id) {
        accountService.delete(id);
    }
}
