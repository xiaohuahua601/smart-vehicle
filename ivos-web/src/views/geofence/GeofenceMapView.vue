<!--围栏地图页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">新增围栏</span>
    <el-button type="primary" style="float:right;margin-top:13px;"
               @click="router.push('/geofence')">返回</el-button>
  </div>
  <!-- 第6步:创建地图容器元素 -->
  <div id="mapContainer" style="width: 100%; height:84vh;"></div>
</template>

<script setup>
import router from "@/router";
import {nextTick, onMounted, ref} from "vue";
import { DrawScene, DrawControl,OperateEventType } from 'bmap-draw';

onMounted(()=>{
  //表示在DOM更新之后再执行,因为我们要确保上方div创建之后再给div里渲染地图
  nextTick(()=>{
    //第7步:创建地图示例,注意:不要引入,忽略提示!
    let map = new BMapGL.Map("mapContainer");
    //第8步:设置中心点坐标，设置经纬度
    let point = new BMapGL.Point(116.4074, 39.9024);
    //第9步：设置地图显示的中心点与地图缩放级别
    map.centerAndZoom(point, 15);

    //添加地图绘制控件
    //1.创建绘制场景类,这是其他绘制控件使用的基类,注意:DrawScene需要导入!
    const scene = new DrawScene(map);
    //2.创建绘制控件类,并将上一回创建好的绘制场景作为构造参数传入,注意:DrawControl也需要导入!
    const drawContrl = new DrawControl(scene, {
      //3.绘制控件配置项:
      //3.1不显示对应图标的用途提示
      enableTips: false,
      //3.2 设置绘制控件的显示位置(右上角)
      anchor: BMAP_ANCHOR_TOP_RIGHT,
      //3.3 设置绘制控件显示哪些绘制项 circle圆形 rectangle矩形
      drawingItems: [
        'circle',
        'rectangle'
      ]
    });
    //5.监听绘制完成事件,返回触发完成事件的电子围栏对象
    //注意: OperateEventType需要导入!
    scene.addEventListener(OperateEventType.COMPLETE,(event)=>{
      console.log(event.target);
      //6.从触发完成事件的围栏对象中获取围栏数据
      let overlay = event.target.overlay;
      //7.判断绘制的图形是圆形还是矩形
      //instanceof用来判断对象overlay是否为BMapGL.Circle类的实例
      if(overlay instanceof BMapGL.Circle){//绘制的围栏是圆形
        //设置围栏类型为圆形
        geofence.value.position.type = 'circle';
        //设置圆形围栏的半径值
        geofence.value.position.radius = overlay.getRadius();
        //设置圆形围栏圆心的经度与纬度值
        //百度地图默认使用的就是国标CA坐标,无需转换,使用get方法可直接获得
        geofence.value.position.latitude = overlay.getCenter().lat;
        geofence.value.position.longitude = overlay.getCenter().lng;
        console.log(geofence.value);
        console.log(geofence.value.position);
      }else{//绘制的围栏是矩形
            //定义数组用来保存矩形四个定点的坐标值
        let recPoints = [];
        //依次获取overlay的四个顶点坐标值
        for (let i = 0; i < 4; i++) {
          //循环四次,依次取出每个顶点的经纬度,将当前定点的经纬度值使用 - 拼接,并存入recPoints数组中
          // [
          //   "116.40262101468684-39.91095127922452",
          //   "116.40930440768108-39.91095127922452",
          //   "116.40930440768108-39.90608082401607",
          //   "116.40262101468684-39.90608082401607"
          // ]
          recPoints.push(overlay.points[i].latLng.lng+'-'+overlay.points[i].latLng.lat);
        }
        //设置围栏类型为矩形
        geofence.value.position.type = 'rectangle';
        //设置围栏顶点字符串,每个顶点间使用逗号分隔
        geofence.value.position.recPoints = recPoints.join(',');
        console.log(geofence.value);
        console.log(geofence.value.position);
      }
    })


    //4.将绘制控件添加到地图上
    map.addControl(drawContrl);
  })
})

//8.定义电子围栏对象用于给后端新增时发送
const geofence = ref({
  name:'',//围栏名称
  position: {}//围栏信息
})

</script>

<style scoped>

</style>