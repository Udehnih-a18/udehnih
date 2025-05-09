package id.ac.ui.cs.advprog.udehnihh.authentication.service;

public interface TokenBlacklistService {
    public void blacklistToken(String token);
    public boolean isTokenBlacklisted(String token);
}
