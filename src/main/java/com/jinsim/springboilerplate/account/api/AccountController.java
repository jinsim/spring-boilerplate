package com.jinsim.springboilerplate.account.api;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.dto.UpdateAccountReqDto;
import com.jinsim.springboilerplate.account.dto.MyAccountResDto;
import com.jinsim.springboilerplate.account.dto.SignupReqDto;
import com.jinsim.springboilerplate.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequestMapping("/accounts")
@RequiredArgsConstructor // final이 붙은 필드에 대해 생성자를 만들어준다.
public class AccountController {

    private final AccountService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MyAccountResDto signup(@RequestBody @Valid final SignupReqDto requestDto) {
        Long accountId = userService.signup(requestDto);
        Account findAccount = userService.findById(accountId);
        return new MyAccountResDto(findAccount);
    }

    @GetMapping("/{id}")
    public MyAccountResDto getMyAccount(@PathVariable final Long id) {
        Account account = userService.findById(id);
        return new MyAccountResDto(account);
    }

    @PutMapping("{id}")
    public MyAccountResDto updateMyAccount(@PathVariable final Long id,
                                           @RequestBody final UpdateAccountReqDto requestDto) {
        userService.update(id, requestDto);
        Account account = userService.findById(id);
        return new MyAccountResDto(account);
    }

    @DeleteMapping("/{id}")
    public MyAccountResDto deleteMyAccount(@PathVariable final Long id) {
        userService.delete(id);
        Account account = userService.findById(id);
        return new MyAccountResDto(account);
    }
}
