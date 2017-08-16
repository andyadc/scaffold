package com.andyadc.scaffold.showcase.auth.message;

import com.andyadc.scaffold.middleware.message.Message;

/**
 * @author andy.an
 * @since 2017/8/16
 */
public class UserLoginMessage extends Message<UserLoginDto> {

    private UserLoginDto dto;

    public UserLoginMessage(UserLoginDto dto) {
        this.dto = dto;
    }

    @Override
    public UserLoginDto getBody() {
        return dto;
    }

    @Override
    public void setBody(UserLoginDto body) {
        this.dto = body;
    }
}
