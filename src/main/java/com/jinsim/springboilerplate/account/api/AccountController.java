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
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteMyAccount(@PathVariable final Long id) {
        accountService.delete(id);
    }
}
