package com.mystical.cloud.entrance.controller;

import com.mystical.cloud.entrance.base.AbstractService;
import com.mystical.cloud.entrance.base.BaseService;
import com.mystical.cloud.entrance.base.Dispatch;
import com.mystical.cloud.entrance.bean.BaseDto;
import com.mystical.cloud.entrance.exception.EventBaseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MysticalYcc
 */
@RestController
@RequestMapping("demo")
public class EntranceController implements Dispatch<BaseDto> {


    @Override
    @RequestMapping("/{source}/{operation}")
    public String dispatch(@PathVariable("source") String source, @PathVariable("operation") String operation, @RequestBody BaseDto baseDto) {
        String dispose;
        baseDto.setOperation(operation);
        baseDto.setSource(source);
        BaseService<BaseDto> baseDtoBaseService = AbstractService.getOperation(source).orElseThrow(() -> new EventBaseException("未找到" + operation + "相关实现"));
        dispose = baseDtoBaseService.dispose(baseDto);
        return dispose;
    }
}
