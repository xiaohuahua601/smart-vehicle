package cn.tedu.ivos.application.service.impl;

import cn.tedu.ivos.application.mapper.ApplicationMapper;
import cn.tedu.ivos.application.pojo.dto.ApplicationQuery;
import cn.tedu.ivos.application.pojo.dto.ApplicationSaveParam;
import cn.tedu.ivos.application.pojo.entity.Application;
import cn.tedu.ivos.application.pojo.vo.ApplicationVO;
import cn.tedu.ivos.application.service.ApplicationService;
import cn.tedu.ivos.audit.mapper.AuditMapper;
import cn.tedu.ivos.audit.pojo.vo.AuditVO;
import cn.tedu.ivos.audit.service.AuditService;
import cn.tedu.ivos.base.enums.ApplicationStatusEnum;
import cn.tedu.ivos.user.mapper.UserMapper;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import cn.tedu.ivos.vehicle.mapper.VehicleMapper;
import cn.tedu.ivos.vehicle.pojo.entity.Vehicle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Transactional
@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    AuditService auditService;
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    VehicleMapper vehicleMapper;

    @Override
    public void save(ApplicationSaveParam applicationSaveParam) {
        Application application = new Application();
        BeanUtils.copyProperties(applicationSaveParam,application);
        //设置新增申请单的状态是"已发起"
        application.setStatus(ApplicationStatusEnum.PENDING.getCode());
        application.setCreateTime(new Date());
        //由于业务原因,没有修改,根据业务,可以实现撤销,重新发起
        //因为即将要生成的审批单需要设置申请单的id,但申请单的id无法通过任何条件select查询获取
        //所以需要给applicationMapper.xml中对应的insert语句进行配置:
        //useGeneratedKeys="true" keyProperty="id"
        //这两句代码的作用是让jdbc为当前的application对象自动生成自增主键
        //并将自增主键赋值给application对象的id字段
        applicationMapper.insert(application);

        //调用审核模块的业务层方法,新增当前申请单对应的审批单
        auditService.insertAudit(application);
    }

    @Override
    public List<ApplicationVO> selectApplication(ApplicationQuery applicationQuery) {
        //根据前端传过来的查询条件,查到所有符合条件的申请单
        List<ApplicationVO> list = applicationMapper.selectApplication(applicationQuery);
        //依次遍历取出每一个申请单
        for(int i =0; i<list.size() ;i++){
            ApplicationVO applicationVO = list.get(i);
            //车辆申请单需要审批人姓名与id
            //需要在这个自定义方法中查到当前循环到的申请单对应的所有审批人信息并存入ApplicationVO
            assignAuditUserList(applicationVO);
        }
        return list;
    }

    @Override
    public void cancel(Long id) {
        Application application = new Application();
        application.setId(id);
        //修改申请单状态为"撤销"
        application.setStatus(ApplicationStatusEnum.CANCEL.getCode());
        applicationMapper.update(application);
        //删除此申请对应的所有审批单数据
        auditMapper.deleteByApplicationId(id);
    }

    @Override
    public void distribute(Long applicationId, Long vehicleId) {
        //更新申请单表数据
        Application application = new Application();
        application.setId(applicationId);
        application.setVehicleId(vehicleId);
        application.setStatus(ApplicationStatusEnum.ALLOCATION.getCode());
        application.setUpdateTime(new Date());
        applicationMapper.update(application);
        //更新车辆表数据
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setStatus("2");
        vehicleMapper.update(vehicle);
    }

    @Override
    public void back(Long applicationId, Long vehicleId) {
        //更新申请单表数据
        Application application = new Application();
        application.setId(applicationId);
        application.setVehicleId(vehicleId);
        //设置申请单的状态为"工单结束END"
        application.setStatus(ApplicationStatusEnum.END.getCode());
        application.setUpdateTime(new Date());
        applicationMapper.update(application);
        //更新车辆表数据
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setStatus("1");//设置车辆状态为"空闲"
        vehicleMapper.update(vehicle);
    }

    /**
     * 给applicationVO的auditUserIdList与auditUsernameList字段赋值
     * 也就是处理当前申请单的审批人相关信息
     * @param applicationVO
     */
    private void assignAuditUserList(ApplicationVO applicationVO) {
        //准备空集合,分别用来存当前申请单对应的审批人姓名与审批人id
        List<String> auditUsernameList = new ArrayList<>();
        List<Long> auditUserIdList = new ArrayList<>();
        //根据传入车辆申请单,查询对应的审批单数据(注意:一个申请单可以对应多个审批单!)
        List<AuditVO> auditVOList = auditMapper.selectAuditByApplicationId(applicationVO.getId());
        //遍历查出来的审批单集合,依次取出每一个审批单对象
        for(int i = 0; i<auditVOList.size() ;i++){
            //取出当前遍历到的审批单对象
            AuditVO auditVO = auditVOList.get(i);
            //获取当前审批单的id,并将id存入上方准备的auditUserIdList集合中
            Long id = auditVO.getAuditUserId();
            auditUserIdList.add(id);
            //根据审批单中的审批人id,到用户数据层查出用户信息
            UserVO userVO = userMapper.selectById(id);
            //再将用户姓名存入上方准备的auditUsernameList集合中
            auditUsernameList.add(userVO.getUsername());
        }
        //将准备好的审批人id集合数据赋值给applicationVO
        applicationVO.setAuditUserIdList(auditUserIdList);
        //需要准备一个工具,帮我们进行值与值的拼接
        //stringJoiner.add("tom");stringJoiner.add("mike");//tom,mike
        StringJoiner stringJoiner = new StringJoiner(",");
        for(String username : auditUsernameList){
            stringJoiner.add(username);
        }
        //将拼接好的审批人name列表转为一个完整的逗号分隔的字符串赋值给申请单VO的审批人姓名list属性中
        applicationVO.setAuditUsernameList(stringJoiner.toString());
    }
}













