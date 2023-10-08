<template>
    <div>
        <div>
            <!--主题-->
            <el-card
                    class="box-card"
                    shadow="never"
            >
                <div
                        slot="header"
                        class="has-text-centered"
                >
                    <p class="is-size-5 has-text-weight-bold">{{ topic.title }}</p>
                    <div class="has-text-grey is-size-7 mt-3">
                        <span>{{ dayjs(topic.createTime).format('YYYY/MM/DD HH:mm:ss') }}</span>
                        <el-divider direction="vertical" />
                        <span>Publisher：{{ topicUser.alias }}</span>
                        <el-divider direction="vertical" />
                        <span>Views：{{ topic.view }}</span>
                        <el-divider direction="vertical" />
                        <span>Likes：{{ topic.likes }}</span>
                        <el-divider direction="vertical" />
                        <span>Liked：{{ topic.liked }}</span>
                    </div>
                </div>

                <div class="content" v-html="this.content_modify">
                </div>



                <!--标签-->
                <nav class="level has-text-grey is-size-7 mt-6">
                    <div class="level-left">
                        <p class="level-item">
                            <b-taglist>
                                <el-tag v-for="(tag, index) in tags" :key="index">{{ "#" + tag.name }}</el-tag>
                           <!--     <router-link
                                        v-for="(tag, index) in tags"
                                        :key="index"
                                        :to="{ name: 'tag', params: { name: tag.name } }"
                                >
                                    <el-tag>{{ "#" + tag.name }}</el-tag>
                                </router-link>-->

                            </b-taglist>
                        </p>
                    </div>
                    <div
                            class="level-right"
                    >
<!--                        心-->
                        <!--<div id="heart"></div>-->
                            <!--                        当前页面未点赞就显示能点赞的按钮，点赞成功就显示点赞禁用按钮-->
                        <el-button class="like_button" v-if="topic.liked === false "
                                   type="warning" icon="el-icon-star-off"  circle @click="handleLike(topic.id)" plain></el-button>
                        <el-button class="like_button" v-if="topic.liked === true"
                                   type="warning" icon="el-icon-star-off"  circle @click="handleLike(topic.id)" disabled></el-button>
                        <!--当前页面未点赞就不显示取消点赞按钮，点赞成功就显示取消点赞按钮-->
                        <el-button class="like_button" v-if="topic.liked === true "
                                   type="info" icon="el-icon-star-off"  circle @click="handleCancelLike(topic.id)" plain></el-button>
                        <el-button class="like_button" v-if="topic.liked === false"
                                   type="info" icon="el-icon-star-off"  circle @click="handleCancelLike(topic.id)" disabled></el-button>



<!--                        删除按钮-->
                        <a class="level-item">
                            <div class="delete_text"><el-button type="danger" v-if="token !== 'null'"plain @click="handleDelete(topic.id)" >Delete Post</el-button></div>
                        </a>

                    </div>
                </nav>
            </el-card>
        </div>
      <lv-comments :slug="topic.id"></lv-comments>
    </div>

</template>

<script>
    import { deleteTopic, getTopic,setLike,open,cancelLike } from '../api/post'
    import LvComments from "@/components/Comment/Comments";

    export default {
      components: {LvComments},
      inject:['reload'],
        data() {
            return {
                adminOrAuthor: false,
                topicUserName: '',
                name: '',
                username: '',
                token: '',
                flag: false,
                topic: {
                    content: '',
                    id: this.$route.params.id,
                    likes: '',
                    liked: '',
                },
                tags: [],
                topicUser: {},
                content_modify: '',
            }
        },
        mounted() {
            this.fetchTopic();
        },
        methods: {
            // 初始化
            async fetchTopic() {
                getTopic(this.$route.params.id).then(response => {
                    const { data } = response
                    document.title = data.topic.title
                    this.topic = data.topic
                    this.tags = data.tags
                    this.topicUser = data.user
                    this.topicUserName = data.user.alias
                    this.topic.liked = data.liked
                    this.username = data.user.username
                    this.flag = true
                    this.content_modify = this.topic.content.replace(/\n/g,"<br>")
                })

            },
            handleDelete(id) {
                if(this.token !== "null"){

                    deleteTopic(id).then(value => {
                        const { code, message } = value
                        //alert(message)
                        if (code === 200) {
                            setTimeout(() => {
                                this.$router.push({ path: '/home' })
                            }, 500)
                        }
                    })
                }else {
                    open("You need to log in before deleting");
                    this.$router.push({path: "/"});
                }

            },

            handleLike(id){
                if(this.token !== "null"){

                    setLike(id).then(value => {
                        const { code, message } = value
                        //alert(message)
                        if (code === 200) {
                            this.reload();
                            //alert("点赞成功");
                            //location.reload();
                        }else {
                            open("You need to log in before liking");
                            this.$router.push({path: "/"});
                            //已经点过赞 会报错。但是让他能够取消点赞
                            //this.reload();
                        }
                    })
                }else {
                    open("You need to log in before liking");
                    this.$router.push({path: "/"});
                }
            },
            handleCancelLike(id){
                if(this.token != null){

                    cancelLike(id).then(value => {
                        const { code, message } = value
                        //alert(message)
                        if (code === 200) {
                            this.reload();
                            //alert("点赞成功");
                            //location.reload();
                        }else{
                            open("You need to log in before canceling liking");
                            this.$router.push({path: "/"});
                            //不能再取消点赞，报错，让他能够点赞
                            //this.reload();
                        }
                    })
                }else {
                    open("You need to log in before canceling liking");
                    this.$router.push({path: "/"});
                }
            },

    },
        created(){
            //页面加载时就从本地通过localstorage获取存储的token值
            this.token =  localStorage.getItem('token');

        },


    }


</script>

<style>

    .like_button{
        margin-right: 10px;
    }
    #preview {
        min-height: 300px;
    }

    #heart {
        left: 200px;
        top: 50px;
        width: 100px;
        height: 90px;


    }
    #heart:before,
    #heart:after {
        width: 50px;
        height: 80px;
        left: 50px;
        top: 0px;
        background-color: #C73F65;
        position: absolute;
        content: "";
        -moz-border-radius: 50px 50px 0 0;
        border-radius: 50px 50px 0 0;
        -webkit-transform: rotate(-45deg);
        -ms-transform: rotate(-45deg);
        -moz-transform: rotate(-45deg);
        -o-transform: rotate(-45deg);
        transform: rotate(-45deg);
        -webkit-transform-origin: 0 100%;
        -ms-transform-origin: 0 100%;
        -moz-transform-origin: 0 100%;
        -o-transform-origin: 0 100%;
        tansform-origin: 0 100%;
    }
    #heart:after {
        left: 0;
        -webkit-transform: rotate(45deg);
        -ms-transform: rotate(45deg);
        -moz-transform: rotate(45deg);
        -o-transform: rotate(45deg);
        transform: rotate(45deg);
        -webkit-transform-origin: 100% 100%;
        -ms-transform-origin: 100% 100%;
        -moz-transform-origin: 100% 100%;
        -o-transform-origin: 100% 100%;
        transform-origin: 100% 100%;
    }
</style>
