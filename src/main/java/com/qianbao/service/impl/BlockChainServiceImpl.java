package com.qianbao.service.impl;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author lijiechu
 * @create on 17/9/15
 * @description 区块链服务类
 */
@Service
public class BlockChainServiceImpl {

    private String ORG_TOKEN1;

    private String ORG_TOKEN2;

    private String ORG_TOKEN3;

    private String ORG_TOKEN4;

    public void initBlockChain(){
        String shpath="sh /Users/lijiechu/Documents/test-ABS-chaincode/base-net/init.sh";
        try {
            Process ps = Runtime.getRuntime().exec(shpath);
            ps.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getORG_TOKEN1() {
        return ORG_TOKEN1;
    }

    public void setORG_TOKEN1(String ORG_TOKEN1) {
        this.ORG_TOKEN1 = ORG_TOKEN1;
    }

    public String getORG_TOKEN2() {
        return ORG_TOKEN2;
    }

    public void setORG_TOKEN2(String ORG_TOKEN2) {
        this.ORG_TOKEN2 = ORG_TOKEN2;
    }

    public String getORG_TOKEN3() {
        return ORG_TOKEN3;
    }

    public void setORG_TOKEN3(String ORG_TOKEN3) {
        this.ORG_TOKEN3 = ORG_TOKEN3;
    }

    public String getORG_TOKEN4() {
        return ORG_TOKEN4;
    }

    public void setORG_TOKEN4(String ORG_TOKEN4) {
        this.ORG_TOKEN4 = ORG_TOKEN4;
    }
}
