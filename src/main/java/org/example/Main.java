package org.example;

import Audio.Audio_collect;
import Frame.JFrame_Collect;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        File file = new File("a.wav");
        int id = 0;
        for (int i = 0; i < 100; i++) {
            System.out.println(id++);
        }

    }
}