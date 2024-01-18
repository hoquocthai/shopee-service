package com.example.stripe.controller.publics;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.QRCodeGeneratorService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/qr-code")
@RequiredArgsConstructor
public class QRCodeController {

    private final QRCodeGeneratorService qrCodeGeneratorService;

    @ApiOperation(value = "API to create QR code")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/{id}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> qrcode(@PathVariable String id, HttpServletResponse response) throws Exception {
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(qrCodeGeneratorService.getQRCodeImage(id, 250, 250));
        outputStream.flush();
        outputStream.close();
        return ResponseEntity.noContent().build();
    }
}
