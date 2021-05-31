package com.security.test.consumer.controller;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class FastDfsController {

    FastFileStorageClient fastFileStorageClient;
    FdfsWebServer fdfsWebServer;

    public static void main(String[] args) {

        FastDfsController f0 = null;
        FastDfsController f1 = f0;
        f0 = new FastDfsController();
        List list = new ArrayList<String>();
        list.add("44");
        list.add("666");
        HashSet hashSet = new HashSet<>(list);
    }

}
