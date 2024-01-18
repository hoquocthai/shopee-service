package com.example.stripe.controller.publics;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.UserService;
import com.example.stripe.service.dto.response.UserDetailsResponseDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "API to get user detail")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/user-details", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<UserDetailsResponseDto> getUserDetails(@NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(userService.getUserDetails(httpServletRequest));
    }
}
