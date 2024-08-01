<!--电子围栏地图页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">新增围栏</span>
    <!-- 为返回按钮实现点击返回至上一页"电子围栏管理页" -->
    <el-button @click="router.push('/geofence')" type="primary" style="float:right;margin-top:13px;">返回</el-button>
  </div>
  <!-- 第6步：创建地图容器元素-->
  <div style="width: 100%; height:84vh;" id="mapContainer"></div>
  <!-- 新增电子围栏弹窗 -->
  <el-dialog v-model="dialogVisible" title="围栏信息" :before-close="closeDialog">
    <el-form  label-width="80px">
      <el-form-item label="围栏名称">
        <el-input v-model="geofence.name" placeholder="请输入围栏名称"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="saveGeofence">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import router from "@/router";
import {nextTick, onMounted, ref} from "vue";
import {DrawScene, DrawControl,OperateEventType} from 'bmap-draw';
import axios from "axios";
import qs from "qs";
import {ElMessage} from "element-plus";

//8.定义电子围栏对象
const geofence = ref({
  name:'', //围栏名称
  position:{} //围栏信息
});

//声明变量用于保存地图对象
var map;
onMounted(()=>{
  //nextTick 表示在DOM更新之后执行，确保地图能在div创建之后渲染
  nextTick(()=>{
    //第7步：创建地图实例,放入之前准备好的容器中
    //注意:BMapGL不需要导入！忽略提示
    map = new BMapGL.Map("mapContainer");
    //第8步：设置中心点坐标(天安门)
    let point = new BMapGL.Point(116.4074, 39.9042);
    //第9步: 地图初始化，同时设置地图展示级别 中心点 缩放
    map.centerAndZoom(point, 15);

    // 添加鼠标绘制控件
    //1.创建场景类，这是添加其它绘制控件的前提,DrawScene需要导入！
    const scene = new DrawScene(map);
    //2.创建控件类,并将上一步创建好的场景作为参数传入,DrawControl需要导入！
    const drawContrl = new DrawControl(scene, {
      //3.不显示对应图标用途提示
      enableTips: false,
      //4.控件位于地图的右上角
      anchor: BMAP_ANCHOR_TOP_RIGHT,
      //5.绘制项数组,包含圆形与矩形
      drawingItems: [
        'circle',
        'rectangle'
      ]
    });

    //7.给图形绘制结束添加监听事件,返回触发事件的电子围栏元素
    //OperateEventType 第一次使用需要手动导入！
    scene.addEventListener(OperateEventType.COMPLETE, (event) => {
      //12.绘制完成点击"√",显示新增围栏弹窗
      dialogVisible.value = true;
      console.log(event.target);

      //9.从触发事件的目标对象中获取绘制完成的电子围栏图形元素（overlay）
      let overlay = event.target.overlay;
      //10.绘制的是圆形
      //instanceof 运算符用于判断变量overlay是否为BMapGL.Circle类的对象
      if(overlay instanceof BMapGL.Circle){
        //设置围栏类型为圆形
        geofence.value.position.geofenceType='circle';
        //依次获取圆心经度、纬度并保存到geofence对象的position属性里
        //百度地图默认使用的就是cA坐标值,使用get方法可直接获得,无需转换
        geofence.value.position.longitude = overlay.getCenter().lng;
        geofence.value.position.latitude = overlay.getCenter().lat;
        //获取半径并保存到data对象中
        geofence.value.position.radius = overlay.getRadius();
        console.log(geofence.value.position);//{type: 'circle', longitude: 116.41307729082298, latitude: 39.915518127928436, radius: 626}
      }else {//绘制的是矩形
        //定义数组用来保存矩形的四个顶点坐标
        let recPoints = [];
        //依次获取矩形的四个顶点坐标并保存到数组中
        for(let i = 0; i < 4; i++){
          //overlay.points[i] 5个点 0,1,2,3循环四次,也就是依次获取四个顶点的经纬度值，存入recPoints数组中
          recPoints.push(overlay.points[i].latLng.lng+'-'+overlay.points[i].latLng.lat);
        }
        console.log(recPoints);
        //将数组中的元素用逗号分隔并保存到data对象中
        console.log(recPoints.join(','));
        //设置围栏类型为矩形
        geofence.value.position.geofenceType='rectangle';
        //将顶点坐标数组中的元素用逗号分隔并保存到position属性里
        geofence.value.position.recPoints = recPoints.join(',');
        console.log(geofence.value.position);//{type:'rectangle',recPoints:'116.38124134354936-39.91878313185032,116.38734982101722-39.91878313185032,116.38734982101722-39.91324913449542,116.38124134354936-39.91324913449542'}
      }

    })

    //6.将创建好的绘制控件添加到地图中
    map.addControl(drawContrl);
  })
})

//11.设置新增围栏弹窗默认关闭
const dialogVisible=ref(false);
//13.新增围栏方法
const saveGeofence = ()=>{
  if(!geofence.value.name||geofence.value.name.trim().length==0){
    ElMessage.error('围栏名称不能为空!');
    return;
  }
  let data = qs.stringify(geofence.value);
  axios.post(BASE_URL+'/v1/geofence/save',data).then((response)=>{
    if(response.data.code === 2000){
      ElMessage.success('保存成功!');
      //新增成功,关闭弹窗
      dialogVisible.value = false;
      //跳转至电子围栏管理页
      router.push('/geofence');
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}


//14.定义关闭弹窗的方法
const closeDialog = ()=>{
  //方案一:直接回到围栏列表页
  // if(confirm('确认取消新增围栏?')){
  //   router.push('/geofence');
  // }
  //方案二:还在地图页面,重新绘制围栏
  if(confirm('确认取消新增围栏?')){
    dialogVisible.value = false;
    //15.3 清空地图对象上的图形遮罩
    map.clearOverlays();
  }
}
</script>

<style scoped>
</style>