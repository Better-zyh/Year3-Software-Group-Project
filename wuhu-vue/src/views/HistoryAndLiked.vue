<template>
  <div >
    <div >
      <el-card >
        <div slot="header" class="clearfix">
          <!--                头部标签选项卡组件-选最新主体-选最热主体-->
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <!--                    el-tab-pane选项页，放article标签，for循环拿出多个article-->
            <el-tab-pane label="History Post" name="history">
              <!--                       media类是buma组件 -->
              <article v-for="(item, index) in articleList" :key="index" class="media">

                <div class="media-content">
                  <!--                                文章标题-->
                  <div class="content-title">
                    <p class="ellipsis is-ellipsis-1">
                      <el-tooltip class="item" effect="dark" :content="item.title" placement="top">
                        <router-link :to="{name:'postDetail',params:{id:item.id}}">
                          <span class="is-size-6">{{ item.title }}</span>
                        </router-link>
                      </el-tooltip>
                    </p>
                  </div>


                  <div  class="whiteSpace">
                    <span class="is-size-6">{{ item.content }}</span>
                  </div>


                  <!--文章下边栏-->
                  <nav class="level has-text-grey is-mobile  is-size-7 mt-2">
                    <div class="level-left">
                      <div class="level-left">

                        <!--                                            文章用户名，显示不出来-->
                        <router-link class="level-item" :to="{ path: `/member/${item.username}/home` }">
                          {{ item.alias }}
                        </router-link>
                        <!--                     一个文章有多个标签用span                          -->
                        <span class="mr-1">
                        Published on:{{ dayjs(item.createTime).format("YYYY/MM/DD") }}

                      </span>
                        <!--                     一个文章有多个标签用span                          -->
                        <span
                            v-for="(tag, index) in item.tags"
                            :key="index"
                            class="tag is-hidden-mobile is-success is-light mr-1"
                        >
                        <router-link :to="{ name: 'tag', params: { name: tag.name } }">
                          {{ "#" + tag.name }}
                        </router-link>
                      </span>
                        <!--   移动端这个标签是隐藏的-->
                        <span class="mx-2 is-hidden-mobile">View:{{ item.view }} </span>
                        <span class="mx-2 is-hidden-mobile">Likes:{{ item.likes }}</span>
                        <span class="mx-2 is-hidden-mobile">Comments:{{ item.comments }}</span>
                      </div>
                    </div>
                  </nav>
                </div>
                <!--          <div class="media-right" />-->
              </article>
            </el-tab-pane>

            <el-tab-pane label="Liked Post" name="likedPost">
              <!--                       media类是buma组件 -->
              <article v-for="(item, index) in articleList" :key="index" class="media">
                <!--              <div class="media-left">
                                  &lt;!&ndash;<figure class="image is-48x48">
                                      <img :src="`https://cn.gravatar.com/avatar/${item.userId}?s=164&d=monsterid`" style="border-radius: 5px;">
                                  </figure>&ndash;&gt;
                              </div>-->
                <div class="media-content">
                  <!--                                文章标题-->
                  <div class="content-title">
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

                  <!--文章下边栏-->
                  <nav class="level has-text-grey is-mobile  is-size-7 mt-2">
                    <div class="level-left">
                      <div class="level-left">

                        <!--                                            文章用户名，显示不出来-->
                        <router-link class="level-item" :to="{ path: `/member/${item.username}/home` }">
                          {{ item.alias }}
                        </router-link>
                        <!--                     一个文章有多个标签用span                          -->
                        <span class="mr-1">
                        Published on:{{ dayjs(item.createTime).format("YYYY/MM/DD") }}

                      </span>
                        <!--                     一个文章有多个标签用span                          -->
                        <span
                            v-for="(tag, index) in item.tags"
                            :key="index"
                            class="tag is-hidden-mobile is-success is-light mr-1"
                        >
                        <router-link :to="{ name: 'tag', params: { name: tag.name } }">
                          {{ "#" + tag.name }}
                        </router-link>
                      </span>
                        <!--   移动端这个标签是隐藏的-->
                        <span class="is-hidden-mobile">View:{{ item.view }}</span>
                        <span class="is-hidden-mobile" style="margin-left: 7px">Likes:{{ item.likes }}</span>
                      </div>
                    </div>
                  </nav>
                </div>
                <!--          <div class="media-right" />-->
              </article>
            </el-tab-pane>


          </el-tabs>
        </div>

        <!--分页-->
        <pagination
            v-show= false
            :total="page.total"
            :page.sync="page.current"
            :limit.sync="page.size"
            @pagination="init"
        />


      </el-card>


    </div>

  </div>
</template>

<script>
import {myHistoryPost, getLikedPost, searchByKeyword} from "../api/post";
import MainHeader from '../components/MainHeader.vue'
import Pagination from '../components/Pagination/index'
export default {
  name: 'TopicList',
  components: { Pagination,MainHeader },
  data() {
    return {
      activeName: 'history', //切换帖子选项，默认选择最新的帖子
      articleList: [], //帖子列表
      page: {
        current: '1',
        size: '1', //初始定义 不然后端400拿不到数据
        total: '',
        tab: 'history'
      },

      ids: [], //页面加载时，从articleList里拿到所有贴子的id

      topic: { //一篇贴子内容
        content: '',
        id: '',
      },
    }
  },
  created() {
    this.init("history") //请求帖子列表
  },
  mounted() {
  },
  computed:{
  },
  methods: {
    handleCurrentChange(value){
      this.currentPage = value;

    },
    init(tab) {
      if(tab == "history"){
        //alert( "history");
        //alert(this.page.current);
        myHistoryPost(this.page.current, this.page.size).then(value => {
          //alert("发出去了");
          const { data } = value
          this.page.current = data.current
          this.page.total = data.total
          this.page.size = data.size
          this.articleList = data.records
        })
      }
      if(tab == "likedPost"){
        //alert(this.page.current);
        //alert("liked");
        getLikedPost(this.page.current, this.page.size).then(value => {
          //alert("发出去了");
          const { data } = value
          this.page.current = data.current
          this.page.total = data.total
          this.page.size = data.size
          this.articleList = data.records
        })

      }


    },
    handleClick(tab) {
      this.page.current = 1
      this.init(tab.name)
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
.post-list-all{
  position: absolute;
  /* overflow-y: scroll;*/
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}
.mpage {
  margin: 0 auto;
  text-align: center;
}

</style>
