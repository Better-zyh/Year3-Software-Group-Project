<template>
  <div class="home">
    <div class="header">
      <main-header></main-header>
    </div>

    <div class="main">
      <div class="columns">
        <div class="column is-two-thirds">
          <div id="tag" class="columns">

            <div class="column is-three-quarters">
              <el-card class="box-card" shadow="never">
                <div slot="header" class="">
                  🔍 Searched <span class="has-text-info">{{ topics.length }}</span> about
                  <span class="has-text-info">{{ this.$route.params.name }}</span>
                  topic of conversation
                </div>

                <div class="text item">
                  <article v-for="(item, index) in topics" :key="index" class="media mt-3">
                    <div class="media-content">
                      <div class="content">
                        <el-tooltip class="item" effect="dark" :content="item.title" placement="top">
                          <router-link :to="{ name: 'postDetail',params:{id: item.id } }">
                            {{ item.title }}
                          </router-link>
                        </el-tooltip>
                      </div>

                      <nav class="level has-text-grey is-size-7">
                        <div class="level-left">
                          <span>发布于:{{ dayjs(item.createTime).format('YYYY-MM-DD HH:mm:ss') }}</span>

                          <span class="mx-2 is-hidden-mobile">View:{{ item.view }}</span>
                          <span class="mx-2 is-hidden-mobile">Likes:{{ item.likes }}</span>
                          <span class="mx-2 is-hidden-mobile">Comments:{{ item.comments }}</span>
                        </div>
                      </nav>
                    </div>
                  </article>
                </div>
              </el-card>
            </div>

<!--            <div class="column">-->
<!--              <el-card class="box-card" shadow="hover">-->
<!--                <div slot="header" class="clearfix">-->
<!--                  🤙 关于标签-->
<!--                </div>-->
<!--                <div>-->
<!--                  <ul>-->
<!--                    <li class="content">标签由平台用户发布使用</li>-->
<!--                    <li class="content">系统每周会定时清理无用标签</li>-->
<!--                  </ul>-->
<!--                </div>-->
<!--              </el-card>-->
<!--              <el-card shadow="hover">-->
<!--                <div slot="header">-->
<!--                  🏷 热门标签-->
<!--                </div>-->
<!--                <div>-->
<!--                  <ul>-->
<!--                    <li v-for="(tag,index) in tags" :key="index" style="padding: 6px 0">-->
<!--                      <router-link :to="{name:'tag',params:{name:tag.name}}">-->
<!--                <span v-if="index<9" class="tag">-->
<!--                  0{{ parseInt(index) + 1 }}-->
<!--                </span>-->
<!--                        <span v-else class="tag">-->
<!--                  {{ parseInt(index) + 1 }}-->
<!--                </span>-->
<!--                        {{ tag.name }}-->
<!--                      </router-link>-->
<!--                    </li>-->
<!--                  </ul>-->
<!--                </div>-->
<!--              </el-card>-->
<!--            </div>-->
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
import { getTopicsByTag } from '@/api/post'
import SideBar from '../views/sideBar/sideCol'
import Pagination from "@/components/Pagination";
import MainHeader from '../components/MainHeader.vue'

export default {
  name: 'Tag',
  components: { SideBar,MainHeader},
  data() {
    return {
      topics: [],
      tags: [],
      paramMap: {
        name: this.$route.params.name,
        page: 1,
        size: 10
      }
    }
  },
  created() {
    this.fetchList()
  },
  methods: {
    fetchList: function() {
      getTopicsByTag(this.paramMap).then(response => {
        console.log(response)
        this.topics = response.data.topics.records
        // this.tags = response.data.hotTags.records
      })
    }
  }
}
</script>

<style scoped>
#tag {
  min-height: 500px;
}
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
