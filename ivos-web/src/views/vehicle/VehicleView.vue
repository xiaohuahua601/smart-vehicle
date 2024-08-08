<!--车辆管理页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">车辆管理</span>
    <el-button @click="dialogVisible=true" type="primary" style="float:right;margin-top:13px;">新建车辆</el-button>
  </div>
  <!-- 新增车辆弹窗 -->
  <el-dialog :title="dialogTitle" v-model="dialogVisible" style="width:1000px;padding:40px;">
    <el-form label-width="80px" label-position="top">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车辆品牌">
            <el-input placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆牌号">
            <el-input placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车辆型号">
            <el-input placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆识别码">
            <el-input placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车辆排量">
            <el-select placeholder="请选择">
              <el-option label="1.6" value="1"></el-option>
              <el-option label="2.5" value="2"></el-option>
              <el-option label="4" value="3"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆类型">
            <el-select placeholder="请选择">
              <el-option label="轿车" value="10"></el-option>
              <el-option label="货车" value="20"></el-option>
              <el-option label="客车" value="30"></el-option>
              <el-option label="挂车" value="40"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车身颜色">
            <el-select placeholder="请选择">
              <el-option label="黑" value="10"></el-option>
              <el-option label="白" value="20"></el-option>
              <el-option label="蓝" value="30"></el-option>
              <el-option label="灰" value="40"></el-option>
              <el-option label="银" value="50"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="里程数">
            <el-input placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="购买时间">
            <el-date-picker type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上牌时间">
            <el-date-picker type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="购买价格">
            <el-input placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="电池类型">
            <el-select placeholder="请选择">
              <el-option label="铅酸蓄电池" value="10"></el-option>
              <el-option label="镍氢电池" value="20"></el-option>
              <el-option label="钠硫电池" value="30"></el-option>
              <el-option label="二次锂电池" value="40"></el-option>
              <el-option label="空气电池" value="50"></el-option>
              <el-option label="三元锂电池" value="60"></el-option>
              <el-option label="碱性燃料电池" value="70"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button>取消</el-button>
      <el-button type="primary">确定</el-button>
    </template>
  </el-dialog>
  <!-- 车辆搜索卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="车辆品牌">
        <el-input placeholder="请输入内容" style="width:220px;"></el-input>
      </el-form-item>
      <el-form-item label="车牌号" style="width:290px;">
        <el-input placeholder="请输入内容" style="width:220px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button>重置</el-button>
        <el-button type="primary">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <!-- 车辆列表 -->
  <el-card style="margin:20px;">
    <el-table>
      <el-table-column type="index" label="编号" width="80" align="center"/>
      <el-table-column prop="brand" label="车辆品牌" align="center"/>
      <el-table-column prop="license" label="车牌号" align="center"/>
      <el-table-column prop="code" label="车辆识别码" align="center"/>
      <el-table-column prop="type" label="车辆类型" align="center"/>
      <el-table-column prop="buyMoney" label="购买价格" align="center"/>
      <el-table-column prop="buyTime" label="购买时间" align="center"/>
      <el-table-column prop="regTime" label="上牌时间" align="center"/>
      <el-table-column prop="batteryType" label="电池类型" align="center"/>
      <el-table-column prop="status" label="车辆状态" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" size="small">编辑</el-button>
          <el-button link type="primary" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>
<script setup>
import {ref} from "vue";

//控制新增车辆弹窗是否显示
const dialogVisible = ref(false);
//定义变量控制dialog的标题
const dialogTitle = ref('新增车辆');
</script>