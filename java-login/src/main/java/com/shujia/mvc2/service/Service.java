package com.shujia.mvc2.service;

public interface Service {
    public String login(String username, String password);
    public String register(String username, String password, String newpassword);
}
