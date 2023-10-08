<template>
  <header class="main-header" :style="{background:color}">
    <div class="header-content" :style="{background:color}">
      <el-menu
          :style="{background:color}"
          mode="horizontal"
         >
        <el-menu-item index="1">
          <el-button icon="el-icon-s-home" type="text" v-on:click="homepage">Home</el-button>
        </el-menu-item>
        <el-menu-item index="1">
<!--          <el-dropdown split-button type="primary">-->
<!--            set color-->
<!--            <el-dropdown-menu slot="dropdown">-->
<!--              <el-dropdown-item @click="handleClick">黄金糕</el-dropdown-item>-->
<!--              <el-dropdown-item @click="handleClick">狮子头</el-dropdown-item>-->
<!--              <el-dropdown-item @click="handleClick">螺蛳粉</el-dropdown-item>-->
<!--              <el-dropdown-item @click="handleClick">双皮奶</el-dropdown-item>-->
<!--              <el-dropdown-item @click="handleClick">蚵仔煎</el-dropdown-item>-->
<!--            </el-dropdown-menu>-->
<!--          </el-dropdown>-->
          <el-color-picker
              v-model="color"
              show-alpha
              :predefine="predefineColors">
          </el-color-picker>

        </el-menu-item>
        <el-input  v-model="searchKey" size="medium" class="searchbox" placeholder="Input comment" @keyup.enter.native="search()">
          <template slot="append">
            <el-dropdown @command="handleCommand" split-button type="primary" @click="search()">
              {{searchType}}
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="Search by Topic">Search by Topic</el-dropdown-item>
                <el-dropdown-item command="Search by Tag">Search by Tag</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-input>
        <div class="LogOrInfor" v-if="this.windowWidth >= this.iniWidth*1.5">
          <div class="" v-if="this.name != 'null'">
            <el-button icon="el-icon-circle-close" type="text" v-on:click="logout">Log Out</el-button>
          </div>
          <div class="" v-if="this.name === 'null'">
            <el-button type="primary" @click ="logout">Log in</el-button>
          </div>
        </div>
        <div v-if="this.name != 'null'">
          <div class = "head" >
            <ul>
              <li :class="li.class"  v-for="li in licontainer" @mouseover="changeActive($event)" @mouseleave="removeActive($event)" >
                <div v-if = "src === 'null'">
                  <img src="../assets/img/head.jpg" id ="headimg"   @mouseover="showTip(event)" @click="userinfor" @mouseleave="hideTip(event)"  >
                </div>
                <div v-if = "src != 'null'">
                  <img src="../assets/img/head.jpg" id ="headimg2"  @mouseover="showTip(event)" @click="userinfor" @mouseleave="hideTip(event)" >
                </div>
                <el-card shadow = "always" class="userinformation" id="divTip1" @mouseover="showTip(event)">
                  <div slot="header" class="clearfix">
                    <span class="username">{{username}}</span>
                  </div>
                  <div  class="text item">
                    <router-link :to="'/Profile'">Personal Center</router-link>
                  </div>
                  <div  class="text item">
                    {{email}}
                  </div>
                  <div  class="text item">
                    {{"Credit: "+score}}
                  </div>
                  <div  class="text item">
                    {{"Occupation: "+bio}}
                  </div>
                  <div  class="text item">
                    <el-link href="" target="">Log Out</el-link>
                  </div>
                </el-card>
              </li>
            </ul>
          </div>
        </div>
      </el-menu>
    </div>
<!--    <div v-if="this.name != 'null'">
&lt;!&ndash;    <div class = "head" >
      <ul>
        <li :class="li.class"  v-for="li in licontainer" @mouseover="changeActive($event)" @mouseleave="removeActive($event)" >
             <div v-if = "src === null">
          <img src="../assets/img/head.jpg" id ="headimg"   @mouseover="showTip(event)" @click="userinfor" @mouseleave="hideTip(event)"  >
               </div>
          <div v-if = "src != null">
            <img src="../assets/img/head.jpg" id ="headimg2"  @mouseover="showTip(event)" @click="userinfor" @mouseleave="hideTip(event)" >
          </div>
          <el-card shadow = "always" class="userinformation" id="divTip1" @mouseover="showTip(event)">
            <div slot="header" class="clearfix">
              <span class="username">{{username}}</span>
            </div>
            <div  class="text item">
              <router-link :to="'/Profile'">Personal Center</router-link>
            </div>
            <div  class="text item">
              {{email}}
            </div>
            <div  class="text item">
              {{"Credit: "+score}}
            </div>
            <div  class="text item">
              {{"Occupation: "+bio}}
            </div>
            <div  class="text item">
              <el-link href="" target="">Log Out</el-link>
            </div>
          </el-card>
        </li>
      </ul>
    </div>&ndash;&gt;
    </div>-->

  </header>
</template>

<script>

import {getInfoByName} from "../api/post";

export default {
  name: 'MainHeader',
  data() {
    return {
      searchType: 'Search by Topic',
      src: '',
      avatar: '../assets/img/head.jpg',
      seen :false,
      licontainer:[{
        class: "box_ul_li",

      }],
      windowWidth: document.documentElement.clientWidth,  //实时屏幕宽度
      windowHeight: document.documentElement.clientHeight,   //实时屏幕高度
      iniWidth:'',
      iniHeight:'',
      keywords: "",
      username:'',
      activeIndex2: '1',
      email:'',
      bio:'',
      create_time:'',
      score:'',
      searchKey: '',
      searching: false,
      active:false,
      color: 'rgba(121, 196, 238, 0.68)',
      predefineColors: [
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577'
      ]
    };
  },
  methods: {
    handleCommand(command) {
      this.searchType = command;
    },
    changeActive($event) {
      this.seen = true;
      $event.currentTarget.className = 'head_ul_on';
    },

    removeActive($event) {
      this.seen = false;
      $event.currentTarget.className = 'head_ul_li';
    },
    logout()  {
      this.$router.push({path: "/"});
    },
    homepage()  {
      this.$router.push({path: "../home"});
    },
    userinfor(){
      if(this.active == true ) {
        this.$router.push({path: "../Profile"});
      }else{
        this.$router.push({path: "../identifying"});
      }
      },

    hideTip(oEvent) {
      setTimeout(() =>{
        var oDiv = document.getElementById("divTip1");
        oDiv.style.visibility = "hidden";
      },2000);

    },

    showTip(oEvent) {
      var oDiv = document.getElementById("divTip1");
      oDiv.style.visibility = "visible";
      oDiv.style.left = oEvent.clientX - 5 ;
      oDiv.style.top = oEvent.clientY - 5;
    },
    async fetchTopic() {
        getInfoByName().then(response => {
          const {data} = response
          this.topicUser = data
          this.topicUserName = data.alias
          this.username = data.alias
          this.email = data.email;
          this.bio = data.bio;
          this.create_time = data.create_time
          this.score = data.score
          this.src = data.avatar
          this.active = data.active
        })
    },
    search() {
      if(this.searchType === "Search by Topic"||this.searchType === "Search"){
        // alert("按主题搜索"+this.searchKey);
        this.searching = true;
        if (this.searchKey.trim() === null || this.searchKey.trim() === '') {
          this.$message.info({
            showClose: true,
            message: 'Input words for searching！',
            type: 'warning'
          })
          return false
        }
        this.$router.push({ path: '/search?key=' + this.searchKey })
      }
      if(this.searchType === "Search by Tag"){
        // alert("按标签搜索"+this.searchKey)
        this.searching = true;
        if (this.searchKey.trim() === null || this.searchKey.trim() === '') {
          this.$message.info({
            showClose: true,
            message: 'Input words for searching！',
            type: 'warning'
          })
          return false
        }
        this.$router.push({ path: '/tag/' + this.searchKey })
      }
    },

  },
  created(){
    this.token =  localStorage.getItem('token');
    this.name = localStorage.getItem('username');
    if( this.name != 'null'){
    this.fetchTopic();}
    //页面加载时就从本地通过localstorage获取存储的token值
    this.iniWidth = document.documentElement.clientHeight;
    this.iniHeight = document.documentElement.clientWidth;

    //alert(this.topicUserName);
  },
  mounted() {
    var that = this;
    // <!--把window.onresize事件挂在到mounted函数上-->
    window.onresize = () => {
      return (() => {
        window.fullHeight = document.documentElement.clientHeight;
        window.fullWidth = document.documentElement.clientWidth;
        that.windowHeight = window.fullHeight;  // 高
        that.windowWidth = window.fullWidth; // 宽
      })()
    }
  }
}
</script>
<style scoped>
.main-header{
  possition:relative;
}
.searchbox{
  margin-top:10px;
  width:40%;
  margin-left:10%;
  float:left;
}
.LogOrInfor{
  margin-top:12px;
  width:10%;
  margin-left:17%;
  height:5%;
  float:left;
}

.head{
  width:10%;
  height:10%;
  right:5%;
  padding: 15px;
  top:-10px;
  position:absolute;
  margin-bottom:50px;
}
.el-dropdown {
  vertical-align: top;
}
.el-dropdown + .el-dropdown {
  margin-left: 20px;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.userinformation{
  right:15%;
  width: 250px;
  position: absolute;
  height:300px;
  visibility:hidden;
  z-index: 5;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}
.text {
  font-size: 14px;
}
.menu{
  background-color: color;
}
.item {
  margin-bottom: 18px;
  border-bottom: #e8e8ed;
  border: antiquewhite;
}
.username{
  display: flex;
  justify-content: center;
  border-bottom: #e8e8ed;
}
.head_ul_on {
  width:60px;height:60px;
}
.head_ul_li {
  width:50px;height:50px;
  transition: all .6s ease-out;
}
.box_ul_li{
  width:50px;height:50px;
}

</style>
