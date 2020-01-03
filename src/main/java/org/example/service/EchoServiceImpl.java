package org.example.service;

import org.example.annotation.MethodLog;
import org.springframework.stereotype.Service;

@Service("echoService")
public class EchoServiceImpl implements EchoService{

    @MethodLog
    @Override
    public String echo(String str) {
        System.out.println("echo: " + str);
        return str;
    }
}
