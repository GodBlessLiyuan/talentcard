package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.BannerMapper;
import com.talentcard.common.pojo.BannerPO;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.BannerDTO;
import com.talentcard.web.service.IBannerService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.vo.BannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 19:55
 * @description: banner配置
 * @version: 1.0
 */
@Service
public class BannerServiceImpl implements IBannerService {
    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    private ILogService logService;
    @Override
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<BannerPO> page = PageHelper.startPage(pageNum, pageSize);
        List<BannerPO> pos = bannerMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), BannerVO.convert(pos)));
    }

    @Override
    public ResultVO insert(HttpSession session,BannerDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        BannerPO existPO = bannerMapper.queryByName(dto.getName());
        if(null != existPO) {
            return new ResultVO(1103);
        }
        BannerPO po = BannerDTO.buildPO(new BannerPO(), dto);
        po.setCreateTime(new Date());
        po.setStatus((byte) 2);
        po.setDr((byte) 1);
        bannerMapper.insert(po);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_BannerConfig,
                "新增%s推广",dto.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO status(HttpSession session,Long bid, Byte status) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        BannerPO po = bannerMapper.selectByPrimaryKey(bid);
        if (null == po) {
            return new ResultVO(1101);
        }

        if (1 == status) {
            po.setUpdateTime(new Date());
        } else if (2 == status) {
            po.setUpdateTime(null);
        }
        po.setStatus(status);
        bannerMapper.updateByPrimaryKey(po);

        this.redisMapUtil.del(TalentConstant.BANNER_INFO);
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_BannerConfig,
                "%s架%s推广",1 == status?"上":"下",po.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO delete(HttpSession session,Long bid) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        BannerPO bannerPO = bannerMapper.selectByPrimaryKey(bid);
        bannerMapper.deleteByPrimaryKey(bid);
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_BannerConfig,
                "删除%s推广",bannerPO==null?"":bannerPO.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO upload(HttpSession session,MultipartFile file) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getBannerDir(), "banner");
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_BannerConfig,
                "%s上传了推广文件",(String) session.getAttribute("username"));
        return new ResultVO<>(1000, filePathConfig.getPublicBasePath() + picture);
    }
}
