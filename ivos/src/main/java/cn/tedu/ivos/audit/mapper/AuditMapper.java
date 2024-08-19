package cn.tedu.ivos.audit.mapper;

import cn.tedu.ivos.audit.pojo.dto.AuditQuery;
import cn.tedu.ivos.audit.pojo.entity.Audit;
import cn.tedu.ivos.audit.pojo.vo.AuditVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditMapper {
    //新增
    void insert(Audit audit);
    //根基申请单id 查询审批集合
    List<AuditVO> selectAuditByApplicationId(Long id);

    void deleteByApplicationId(Long id);
//查询审批单
    List<AuditVO> selectAudit(AuditQuery auditQuery);
//修改审批单
    void update(Audit audit);
    //查询满足要求的审批单总数
    Integer selectRestAuditCount(AuditQuery auditQuery);

    void deleteById(Long id);
}
