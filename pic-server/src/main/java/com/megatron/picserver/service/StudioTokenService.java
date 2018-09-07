package com.megatron.picserver.service;

import com.megatron.picserver.pojo.StudioToken;
import com.megatron.picserver.utils.base.BaseService;

public interface StudioTokenService extends BaseService<StudioToken, Long> {

    /**
     * @param studioToken 根据 studioToken 获取 小程序 appid  secret
     * @return
     */
    StudioToken getAppIdParamsByStudioToken(String studioToken);

}
