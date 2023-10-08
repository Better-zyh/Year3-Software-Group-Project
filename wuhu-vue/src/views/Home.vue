<template>
    <div class="home">
        <div class="header">
          <main-header></main-header>
        </div>

        <div class="main">
            <div class="columns">
                <div class="column">
                    <div v-if="searching === false">
                        <TopicList></TopicList>
                    </div>
                </div>

               <!-- <div class="aside">
                    <SideBar></SideBar>
                </div>-->
            </div>
        </div>

        <div class="aside" v-if="this.windowWidth >= this.iniWidth*1.5">
            <SideBar></SideBar>
        </div>

    </div>
</template>

<script>
    import PostList from '../views/Post'
    import SideBar from '../views/sideBar/sideCol'
    import MainHeader from '../components/MainHeader.vue'

    export default {
        name: 'Home',
        components: {SideBar,TopicList: PostList,MainHeader },
        data(){
            return {
                // searchType: '搜索',
                token: '',
                input:'',
                searchKey: '',
                searching: false,
                windowWidth: document.documentElement.clientWidth,  //实时屏幕宽度
                windowHeight: document.documentElement.clientHeight,   //实时屏幕高度
                iniWidth:'',
                iniHeight:'',
            }
        },
        methods:{
          // handleCommand(command) {
          //   this.searchType = command;
          // },
            logout()  {
                localStorage.removeItem('token');
                localStorage.removeItem('username');
                this.$router.push({path: "/"});
            },
            // search() {
            //     if(this.searchType === "按主题搜索"||this.searchType === ""){
            //       // alert("按主题搜索"+this.searchKey);
            //       this.searching = true;
            //       if (this.searchKey.trim() === null || this.searchKey.trim() === '') {
            //           this.$message.info({
            //               showClose: true,
            //               message: '请输入关键字搜索！',
            //               type: 'warning'
            //           })
            //           return false
            //       }
            //       this.$router.push({ path: '/search?key=' + this.searchKey })
            //     }
            //   if(this.searchType === "按标签搜索"){
            //     alert("按标签搜索"+this.searchKey)
            //     this.searching = true;
            //     if (this.searchKey.trim() === null || this.searchKey.trim() === '') {
            //       this.$message.info({
            //         showClose: true,
            //         message: '请输入关键字搜索！',
            //         type: 'warning'
            //       })
            //       return false
            //     }
            //     this.$router.push({ path: '/tag/' + this.searchKey })
            //   }
            // },
        },
        created(){
            //页面加载时就从本地通过localstorage获取存储的token值
            this.token =  localStorage.getItem('token');
            this.iniWidth = document.documentElement.clientHeight;
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
        right: 35%;
        bottom: 0;
        /*width: 1000px;*/
        overflow-y: scroll; /* 当内容过多时y轴出现滚动条 */
        overflow-x: hidden;
    }

    .aside{
        position: absolute;
        top: 15%;
        left: 68%;
        right: 12%;
        bottom: 0;
        margin-left: 10px;
    }

</style>
