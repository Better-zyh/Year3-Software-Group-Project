<template>
    <div class="home">
        <div class="header">
          <main-header></main-header>
        </div>

        <div class="main">
            <div class="columns">
                <div class="column is-two-thirds">
                    <div>
                        <el-card shadow="never">
                            <div slot="header" class="clearfix">
                                Searched <code>{{ list.length }}</code>
                                about <code class="has-text-info">{{ query.keyword }}</code> posting
                            </div>
                            <div>
                                <article v-for="(item, index) in list" :key="index" class="media">
                                    <!-- <div class="media-left">
                                         <figure class="image is-48x48">
                                             <img :src="`https://cn.gravatar.com/avatar/${item.userId}?s=164&d=monsterid`">
                                         </figure>
                                     </div>-->
                                    <div class="media-content">
                                        <div class="">
                                            <p class="ellipsis is-ellipsis-1">
                                                <el-tooltip class="item" effect="dark" :content="item.title" placement="top">
                                                    <router-link :to="{name:'postDetail',params:{id:item.id}}">
                                                        <span class="is-size-6">{{ item.title }}</span>
                                                    </router-link>
                                                </el-tooltip>
                                            </p>
                                        </div>
                                        <!--                                文章内容-->
                                        <div  class="whiteSpace">
                                            <span class="is-size-6">{{ item.content }}</span>
                                        </div>
                                        <nav class="level has-text-grey is-mobile  is-size-7 mt-2">
                                            <div class="level-left">
                                                <div class="level-left">
                                                    <router-link class="level-item" :to="{ path: `/member/${item.username}/home` }">
                                                        {{ item.alias }}
                                                    </router-link>

                                                    <span class="mr-1">
                    Published on:{{ dayjs(item.createTime).format("YYYY/MM/DD") }}
                  </span>

                                                    <span
                                                            v-for="(tag, index) in item.tags"
                                                            :key="index"
                                                            class="tag is-hidden-mobile is-success is-light mr-1"
                                                    >
                    <router-link :to="{ name: 'tag', params: { name: tag.name } }">
                      {{ "#" + tag.name }}
                    </router-link>
                  </span>

                                                    <span class="mx-2 is-hidden-mobile" >View:{{ item.view }}</span>
                                                    <span class="mx-2 is-hidden-mobile"  style="margin-left: 7px">Likes:{{ item.likes }}</span>
                                                    <span class="mx-2 is-hidden-mobile" >Comments:{{ item.comments }}</span>
                                                </div>
                                            </div>
                                        </nav>
                                    </div>
                                    <div class="media-right" />
                                </article>
                            </div>

                            <!--分页-->
                            <pagination
                                    v-show= false
                                    :total="query.total"
                                    :page.sync="query.pageNum"
                                    :limit.sync="query.pageSize"
                                    @pagination="fetchList"
                            />
                        </el-card>
                    </div>

                </div>
                <div class="column">
                    <SideBar></SideBar>
                </div>
            </div>
        </div>

    </div>


</template>

<script>
    import { searchByKeyword } from '../api/post'
    import Pagination from '../components/Pagination'
    import MainHeader from '../components/MainHeader.vue'

    import SideBar from '../views/sideBar/sideCol'
    export default {
        name: 'Search',
        components: { Pagination ,SideBar,MainHeader},
        data() {
            return {
                list: [],
                query: {
                    keyword: this.$route.query.key,
                    pageNum: 1,
                    pageSize: 10,
                    total: 0
                },
                token: '',
                input:'',
                searchKey: '',
                searching: false,
            }
        },
        created() {
            this.fetchList();
            this.token =  localStorage.getItem('token')
        },
        methods: {
            fetchList() {
                searchByKeyword(this.query).then(value => {
                    const { data } = value
                    this.list = data.records
                    this.query.total = data.total
                    this.query.pageSize = data.size
                    this.query.pageNum = data.current
                })
            },
            logout()  {
                localStorage.removeItem('token');
                localStorage.removeItem('username');
                this.$router.push({path: "/"});
            },
        },
    }
</script>

<style scoped>
    .whiteSpace{
        width: 600px;
        white-space: normal;
        /* border: 1px solid #000;*/
        overflow : hidden;
        text-overflow: ellipsis;
        -webkit-line-clamp: 3;  /* 设置超出多少行隐藏 */
        -webkit-box-orient: vertical;
        display: -webkit-box;  /* 设置 display 为 -webkit-box 或者 -webkit-inline-box 时为隐藏状态 */
    }

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
