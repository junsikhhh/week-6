package com.example.community.service;

import com.example.community.respository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<String> getAllNames() {
        return testRepository.findAllNames();
    }

    public String getMessage() {
        return "Hello from TestService!";
    }
}
