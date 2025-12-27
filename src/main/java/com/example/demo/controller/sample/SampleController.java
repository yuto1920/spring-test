package com.example.demo.controller.sample;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.sample.SampleService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/samples")
public class SampleController {

    private final SampleService service;
    @GetMapping
    public SampleDTO index() {
        var entity = service.find();
        return new SampleDTO( entity.getContent(), LocalDateTime.now());
    }
}
