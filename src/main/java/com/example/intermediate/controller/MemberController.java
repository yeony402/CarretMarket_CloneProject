package com.example.intermediate.controller;

import com.example.intermediate.controller.request.KakaoMemberInfoDto;
import com.example.intermediate.controller.request.LoginRequestDto;
import com.example.intermediate.controller.request.MemberRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.KakaoMemberService;
import com.example.intermediate.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;
  private final KakaoMemberService kakaoMemberService;

  @RequestMapping(value = "/api/member/signup", method = RequestMethod.POST)
  public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
    return memberService.createMember(requestDto);
  }

  @RequestMapping(value = "/api/member/login", method = RequestMethod.POST)
  public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                              HttpServletResponse response
  ) {
    return memberService.login(requestDto, response);
  }

  @RequestMapping(value = "/api/auth/member/logout", method = RequestMethod.POST)
  public ResponseDto<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    return memberService.logout(userDetails);
  }

  // oauth2 카카오 로그인
  @RequestMapping(value = "/api/member/kakao/callback", method = RequestMethod.GET)
  public KakaoMemberInfoDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
    log.info(code);
    return kakaoMemberService.kakaoLogin(code, response);
  }
}
