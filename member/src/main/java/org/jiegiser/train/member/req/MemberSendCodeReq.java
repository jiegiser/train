package org.jiegiser.train.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MemberSendCodeReq {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3|4|5|7|8][0-9]{9}$", message = "手机号格式不正确")
    private String mobile;
}
