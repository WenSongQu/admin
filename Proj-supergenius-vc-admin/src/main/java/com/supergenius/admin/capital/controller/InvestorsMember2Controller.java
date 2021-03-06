package com.supergenius.admin.capital.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.supergenius.admin.capital.model.vo.VCFiltrateUserVO;
import com.supergenius.admin.capital.model.vo.VCUserinfoVo;
import com.supergenius.admin.capital.service.*;
import com.supergenius.admin.enums.EIdentityType;
import com.supergenius.admin.utils.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@Api(description="投资者会员2")
@RequestMapping("/investorsmember2")
@RestController
public class InvestorsMember2Controller {
    @Autowired
    IVCUserService ivcUserService;
    @Autowired
    IVCRecordService ivcRecordService;
    @Autowired
    IVCOrganizationService ivcOrganizationService;
    @Autowired
    IVCInvestmentService ivcInvestmentService;
    @Autowired
    IVCOrdersService ivcOrdersService;
    @Autowired
    IVCUserinfoService ivcUserinfoService;
    @ApiOperation("查询保推人列表")
    @GetMapping("/guarantee/list/{pageNum}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "filtrate", value = "过滤条件", paramType = "query", dataTypeClass =VCFiltrateUserVO.class )
    })
    public R<IPage<VCUserinfoVo>> getGuaranteeMembers(@RequestParam(name = "filtrate", required = false) String filtrateStr, @PathVariable int pageNum) {
        log.info(filtrateStr + "--------------");
        VCFiltrateUserVO vcFiltrateUserVO = JSON.parseObject(filtrateStr, VCFiltrateUserVO.class);
        log.info(vcFiltrateUserVO + "--------------");
        IPage<VCUserinfoVo> page = ivcUserService.getGuaranteeMembers(new Page(pageNum, 10), vcFiltrateUserVO);
        if (page.getRecords().size() > 0) {
            return R.ok(page).setCode(200);
        }
        return R.failed(new ErrorCode(401));
    }
    @ApiOperation("查询推荐人列表")
    @GetMapping("/recommend/list/{pageNum}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "filtrate", value = "过滤条件", paramType = "query", dataType = "String")
    })
    public R<IPage<VCUserinfoVo>> getRecommendMembers(@RequestParam(name = "filtrate", required = false) String filtrateStr, @PathVariable int pageNum) {
        log.info(filtrateStr + "--------------");
        VCFiltrateUserVO vcFiltrateUserVO = JSON.parseObject(filtrateStr, VCFiltrateUserVO.class);
        log.info(vcFiltrateUserVO + "--------------");
        IPage<VCUserinfoVo> page = ivcUserService.getRecommendMembers(new Page(pageNum, 10), vcFiltrateUserVO);
        if (page.getRecords().size() > 0) {
            return R.ok(page).setCode(200);
        }
        return R.failed(new ErrorCode(401));
    }
    @ApiOperation("查询召集人列表")
    @GetMapping("/convener/list/{pageNum}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "filtrate", value = "过滤条件", paramType = "query", dataType = "String")
    })
    public R<IPage<VCUserinfoVo>> getConvenerMembers(@RequestParam(name = "filtrate", required = false) String filtrateStr, @PathVariable int pageNum) {
        log.info(filtrateStr + "--------------");
        VCFiltrateUserVO vcFiltrateUserVO = JSON.parseObject(filtrateStr, VCFiltrateUserVO.class);
        log.info(vcFiltrateUserVO + "--------------");
        IPage<VCUserinfoVo> page = ivcUserService.getConvenerMembers(new Page(pageNum, 10), vcFiltrateUserVO);
        if (page.getRecords().size() > 0) {
            return R.ok(page).setCode(200);
        }
        return R.failed(new ErrorCode(401));
    }
    @ApiOperation("审核通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "useruids", value = "用户uid", dataType = "String", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "identity", value = "身份guarantor/convener/recommend", dataType = "String", allowMultiple = false, paramType = "query"),

    })
    @PostMapping("/auditSuccess")
    @Transactional
    public R<Boolean> auditSuccess(String identity,String...useruids){
        try {
            if("guarantor".equals(identity)) {
                ivcUserinfoService.auditSuccess(Arrays.asList(useruids), EIdentityType.guarantor);
            }
            if("convener".equals(identity)) {
                ivcUserinfoService.auditSuccess(Arrays.asList(useruids), EIdentityType.convener);
            }
            if("recommend".equals(identity)) {
                ivcUserinfoService.auditSuccess(Arrays.asList(useruids), EIdentityType.recommend);
            }
            return R.ok(true).setCode(200);

        }catch (Exception e){
            return R.failed(new ErrorCode(500));
        }

    }
    @ApiOperation("审核失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "useruids", value = "用户uid", dataType = "String", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "identity", value = "身份guarantor/convener/recommend", dataType = "String", allowMultiple = false, paramType = "query"),

    })
    @PostMapping("/auditfailed")
    @Transactional
    public R<Boolean> auditFailed(String identity,String...useruids){
        try {
            if("guarantor".equals(identity)) {
                ivcUserinfoService.auditFailed(Arrays.asList(useruids), EIdentityType.guarantor);
            }
            if("convener".equals(identity)) {
                ivcUserinfoService.auditFailed(Arrays.asList(useruids), EIdentityType.convener);
            }
            if("recommend".equals(identity)) {
                ivcUserinfoService.auditFailed(Arrays.asList(useruids), EIdentityType.recommend);
            }
            return R.ok(true).setCode(200);

        }catch (Exception e){
            return R.failed(new ErrorCode(500));
        }
    }
    @ApiOperation("禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "useruids", value = "用户uid", dataType = "String", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "identity", value = "身份guarantor/convener/recommend", dataType = "String", allowMultiple = false, paramType = "query"),

    })
    @PostMapping("/auditban")
    @Transactional
    public R<Boolean> auditBan(String identity,String...useruids){
        try {
            if("guarantor".equals(identity)) {
                ivcUserinfoService.auditBan(Arrays.asList(useruids), EIdentityType.guarantor);
            }
            if("convener".equals(identity)) {
                ivcUserinfoService.auditBan(Arrays.asList(useruids), EIdentityType.convener);
            }
            if("recommend".equals(identity)) {
                ivcUserinfoService.auditBan(Arrays.asList(useruids), EIdentityType.recommend);
            }
            return R.ok(true).setCode(200);

        }catch (Exception e){
            return R.failed(new ErrorCode(500));
        }
    }
    @ApiOperation("启动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "useruids", value = "用户uid", dataType = "String", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "identity", value = "身份guarantor/convener/recommend", dataType = "String", allowMultiple = false, paramType = "query"),

    })
    @PostMapping("/auditstart")
    @Transactional
    public R<Boolean> auditStart(String identity,String...useruids){
        try {
            if("guarantor".equals(identity)) {
                ivcUserinfoService.auditStart(Arrays.asList(useruids), EIdentityType.guarantor);
            }
            if("convener".equals(identity)) {
                ivcUserinfoService.auditStart(Arrays.asList(useruids), EIdentityType.convener);
            }
            if("recommend".equals(identity)) {
                ivcUserinfoService.auditStart(Arrays.asList(useruids), EIdentityType.recommend);
            }
            return R.ok(true).setCode(200);

        }catch (Exception e){
            return R.failed(new ErrorCode(500));
        }
    }

}
