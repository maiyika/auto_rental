package com.coder.rental.service.impl;

import com.coder.rental.entity.Violation;
import com.coder.rental.mapper.ViolationMapper;
import com.coder.rental.service.IViolationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Barry
 * @since 2024-06-16
 */
@Service
public class ViolationServiceImpl extends ServiceImpl<ViolationMapper, Violation> implements IViolationService {

}
