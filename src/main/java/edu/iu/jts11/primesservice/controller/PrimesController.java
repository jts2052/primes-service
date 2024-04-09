package edu.iu.jts11.primesservice.controller;

import edu.iu.jts11.primesservice.rabbitmq.MQSender;
import edu.iu.jts11.primesservice.service.IPrimesService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/primes")
public class PrimesController {
    IPrimesService primesService;
    MQSender MQSender;

    public PrimesController(IPrimesService primesService, MQSender mqSender) {
        this.primesService = primesService;
        this.MQSender = mqSender;
    }

    @GetMapping("/{n}")
    public boolean isPrime(@PathVariable int n) {
        System.out.println("isPrime: " + n);
        boolean result = primesService.isPrime(n);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((Jwt) principal).getSubject();
        System.out.println("username: " + username);
        MQSender.sendMessage(username, n, result);
        return result;
    }
}
