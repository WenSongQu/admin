package com.supergenius.admin.capital.service;

import com.supergenius.admin.capital.model.VCVideoDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zuoyu
 * @since 2019-12-04
 */
public interface IVCVideoService extends IService<VCVideoDO> {
    VCVideoDO getByPid(String pid);

}
