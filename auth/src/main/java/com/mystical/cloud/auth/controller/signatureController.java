package com.mystical.cloud.auth.controller;

import com.mystical.cloud.auth.response.CommonResponse;
import com.mystical.cloud.auth.response.CommonResultEnum;
import com.mystical.cloud.auth.signature.annotation.SignedMapping;
import com.mystical.cloud.auth.signature.entity.SignedParam;
import com.mystical.cloud.auth.signature.service.BaseSignedService;
import com.mystical.cloud.auth.signature.service.MyApplicationRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

/**
 * @author ycc
 */
@RestController
@RequestMapping("/signature")
@Slf4j
public class signatureController {

    private final BaseSignedService baseSignedService;

    @Autowired
    public signatureController(BaseSignedService baseSignedService) {
        this.baseSignedService = baseSignedService;
    }

    @RequestMapping("base")
    public SignedParam base(@RequestBody(required = false) String data) {

        SignedParam signedParam = new SignedParam();
        signedParam.setAppId(MyApplicationRunner.APP_ID);
        signedParam.setData(data);
        signedParam.setTimestamp(System.currentTimeMillis() / 1000);
        signedParam.setNonce(new Random().nextInt());

        Map<String, Object> map = null;
        try {
            map = baseSignedService.object2Map(signedParam);
            String signature = baseSignedService.getSignature(MyApplicationRunner.APP_ID, map);
            signedParam.setSignature(signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedParam;
    }


    @PostMapping("param")
    @SignedMapping
    public CommonResponse<String> testHasSignatureParam(@RequestBody(required = false) SignedParam signedParam) {
        String data = signedParam.getData();

        return new CommonResponse<>(CommonResultEnum.SUCCESS, data);
    }

    @GetMapping("head")
    @SignedMapping
    public CommonResponse<String> testHasSignatureHead(@RequestBody(required = false) String data) {

        return new CommonResponse<>(CommonResultEnum.SUCCESS, data);
    }

    @GetMapping("resubmit")
    @SignedMapping(resubmit = true)
    public CommonResponse<String> testResubmit(@RequestBody(required = false) String data) {

        return new CommonResponse<>(CommonResultEnum.SUCCESS, data);
    }

}
