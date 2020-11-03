package com.mystical.cloud.entrance.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author MysticalYcc
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_chop_auth")
public class AuthorizationDto {

    private String id;
    private String requestor;
    @TableField("create_time")
    private String requestDate = new SimpleDateFormat("yyyy-MM-DD").format(new Date());
    private String authorzationId;
    private String departmentId;
    private String accessUsers;
    private String owner;
    @TableField("authorzation_name")
    private String lAName;
    private String businessJustification;
    private String validFrom;
    private String validUntil;
    private String approve;
    private String legalReviewer;
    private List<DocumentInfo> documentInfo;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private int status;
}

@ToString
@Getter
@Setter
@TableName("t_chop_document")
@NoArgsConstructor
@AllArgsConstructor
class DocumentInfo {
    private String documentName;
    private String fileUrl;
    private List<String> chop;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private int status;
}