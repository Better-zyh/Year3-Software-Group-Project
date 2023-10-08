<template>
  <div class="home">
    <div class="header">
      <main-header></main-header>
<!--      <el-row>
        <el-col :span="3" class="left">
          <img src="../assets/logo.png" alt="XJTLU" height="50px" width = "65px"/>
        </el-col>
        <el-col :span="8" class="center">
          <el-input v-model="input" placeholder="Input content">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="3" class="right">
          <el-button icon="el-icon-circle-plus-outline"></el-button>
        </el-col>
      </el-row>-->
    </div>

    <div class="main">
      <div class="columns">
        <div class="column is-two-thirds">
<!--         详情组件-->
          <DetailList></DetailList>
        </div>
        <div class="column">
          <SideBar></SideBar>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import MainHeader from '../components/MainHeader.vue'
  import DetailList from "./DetailList";
  import SideBar from '../views/sideBar/sideCol'
  export default {
    name: 'POSTDETAIL',
    components: {SideBar,DetailList,MainHeader},
    data(){
      return {
          token: '',
          input:'',
          name: '',
      }
    },
    methods:{
      logout()  {
        this.$router.push({path: "/"});
      },
    },
    created(){
      //页面加载时就从本地通过localstorage获取存储的token值
      this.token =  localStorage.getItem('token');
        this.name = localStorage.getItem('name')
    },
  }
</script>

<style scoped>

  .home{
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
    position: relative;
  }

  /* 头部样式 */
  .header {
    top:0;
    height: 10%;
    left: 0;
    right: 0;
    -webkit-text-fill-color: black;
  }

  .left{
    margin-left: 15%;
  }
  .center{
    margin-left: 15%;
    margin-top: 13px;
  }

  .right{
    margin-left: 1%;
    /* margin-left: 10%;*/
    margin-top: 13px;
  }

  /* 主区域 */
  .main {
    position: absolute;
    top: 15%;
    left: 15%;
    right: 15%;
    bottom: 0;
    /*   width: 1600px;*/
    overflow-y: scroll; /* 当内容过多时y轴出现滚动条 */
    overflow-x: hidden;
  }





</style>
