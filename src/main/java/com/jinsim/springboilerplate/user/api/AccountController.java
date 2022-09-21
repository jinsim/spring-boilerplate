package com.jinsim.springboilerplate.user.api;

import com.jinsim.springboilerplate.user.domain.Account;
import com.jinsim.springboilerplate.user.dto.UpdateAccountReqDto;
import com.jinsim.springboilerplate.user.dto.MyAccountResDto;
import com.jinsim.springboilerplate.user.dto.SignupReqDto;
import com.jinsim.springboilerplate.user.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
