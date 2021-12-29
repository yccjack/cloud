package com.mystical.cloud.auth.service;

public class TestService {

    public static void main(String[] args) {
        DemoService<StrategyExecutor<String>> demoExecutor = o -> o.handler("dc");
        boolean dc = demoExecutor.execute(data -> {
                    if (data.equals("dc")) {
                        BaseStrategyService baseStrategyService = new BaseStrategyService();
                        return baseStrategyService.handler(data);
                    }
                    return false;

                }
        );
        System.out.println(dc);


    }
}
