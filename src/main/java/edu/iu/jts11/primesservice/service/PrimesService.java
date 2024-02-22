package edu.iu.jts11.primesservice.service;

import org.springframework.stereotype.Service;

@Service
public class PrimesService implements IPrimesService {
    @Override
    public boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        for (long i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
