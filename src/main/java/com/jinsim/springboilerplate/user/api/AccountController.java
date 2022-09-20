package com.jinsim.springboilerplate.user.api;

import com.jinsim.springboilerplate.user.domain.Account;
import com.jinsim.springboilerplate.user.dto.SignupReqDto;
import com.jinsim.springboilerplate.user.dto.SignupResDto;
import com.jinsim.springboilerplate.user.repository.UserRepository;
import com.jinsim.springboilerplate.user.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequestMapping("/accounts")
@RequiredArgsConstructor // final이 붙은 필드에 대해 생성자를 만들어준다.
public class AccountController {

    private final AccountService userService;

    @PostMapping
    public SignupResDto signup(@RequestBody @Valid SignupReqDto request) {
        log.info("request={}", request);
        Account account = request.toEntity();
        Long accountId = userService.signup(account);
        Account findAccount = userService.findById(accountId);
        log.info("user = {}, findUser = {}", account, findAccount);
        return new SignupResDto(findAccount);
    }
}
