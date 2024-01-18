package com.example.stripe.controller.publics;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.AuthService;
import com.example.stripe.service.dto.ResetPasswordDto;
import com.example.stripe.service.dto.UserDto;
import com.example.stripe.service.dto.request.LoginDto;
import com.example.stripe.service.dto.request.SignupDto;
import com.example.stripe.service.dto.response.LoginResultDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "API to login")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(value = "/login", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<LoginResultDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @ApiOperation(value = "API to logout")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(value = "/logout", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<String> logout(@NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(authService.logout(httpServletRequest));
    }

    @ApiOperation(value = "API to signup")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(value = "/signup", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(authService.signup(signupDto));
    }

    @ApiOperation(value = "API to reset password")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(value = "/reset-password", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody ResetPasswordDto dto) {
        return ResponseEntity.ok(authService.resetPassword(dto));
    }
}
