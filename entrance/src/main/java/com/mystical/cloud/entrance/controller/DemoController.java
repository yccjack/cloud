package com.mystical.cloud.entrance.controller;

import com.alibaba.fastjson.JSON;
import com.mystical.cloud.entrance.base.AbstractDispatch;
import com.mystical.cloud.entrance.base.BaseService;
import com.mystical.cloud.entrance.bean.BaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MysticalYcc
 */
@RestController
@RequestMapping("demo")
public class DemoController extends AbstractDispatch<String, BaseDto> {


    private BaseService<BaseDto> adHocLongApplication;

    private BaseService<BaseDto> authorization;

    private BaseService<BaseDto> longApplication;

    @Override
    @RequestMapping("/{source}/{operation}")
    public String dispatch(@PathVariable("source") String source, @PathVariable("operation") String operation, @RequestBody String json) {
        String dispose;
        BaseDto baseDto = JSON.parseObject(json, BaseDto.class);
        baseDto.setOperation(operation);
        BaseService<BaseDto> baseDtoBaseService = getSource(source);
        dispose = baseDtoBaseService.dispose(baseDto);
        return dispose;
    }

    @Override
    public void registerService() {
        super.baseServices.put("au", adHocLongApplication);
        super.baseServices.put("lu", longApplication);
        super.baseServices.put("la", authorization);
    }

    @Autowired
    public void setAdHocLongApplication(BaseService<BaseDto> adHocLongApplication) {
        this.adHocLongApplication = adHocLongApplication;
    }

    @Autowired
    public void setAuthorization(BaseService<BaseDto> authorization) {
        this.authorization = authorization;
    }

    @Autowired
    public void setLongApplication(BaseService<BaseDto> longApplication) {
        this.longApplication = longApplication;
    }


}
