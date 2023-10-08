<template>
  <article class="media">
      <span class="touXiang">
          <el-avatar icon="el-icon-user-solid" ></el-avatar>
      </span>
      <div class="media-content">
      <div class="content">
        <p>
          <strong>{{ comment.username }}</strong>
          <small class="ml-2">{{ comment.createTime | date }}</small>
          <br />
          {{ comment.content }}
        </p>
      </div>
    </div>
<!--      游客登录/token===null || username!==评论的name 就让commentDelVisibleL=false
            token!==null && username===评论的name 就让commentDelVisibleL=true
            token!==null && username=== "admin" 就让commentDelVisibleL=true-->
      <span v-if="commentDelVisibleL">
          <el-button type="danger" icon="el-icon-delete" circle @click="handleDelCom(comment.id)"></el-button>
      </span>
  </article>
</template>

<script>
    import {commentDel} from '../../api/post'
export default {
  name: 'LvCommentsItem',
    inject:['reload'],
  props: {
    comment: {
      type: Object,
      required: true
    }
  },
    data(){
      return{
          commentDelVisibleL: false,
          username: '',
          token: '',
      }
    },
    created(){
        //页面加载时就从本地通过localstorage获取存储的token值
        this.token =  localStorage.getItem('token');
        this.username = localStorage.getItem('username');
    },
    mounted() {
        this.delVisibleL();
    },
    methods:{
 /*       游客登录/token===null || username!==comment.username 就让commentDelVisibleL=false
    token!==null && username===评论的name 就让commentDelVisibleL=true
token!==null && username=== "admin" 就让commentDelVisibleL=true*/
        delVisibleL(){
            if(this.token !== "null"){
                if(this.username === "admin" ||this.username === this.comment.username ){
                    this.commentDelVisibleL = true;
                }else{
                    this.commentDelVisibleL = false;
                }
            }
            if(this.token === "null"){
                this.commentDelVisibleL = false;
            }

        },
        handleDelCom(commentId){
            console.log(commentId);
            commentDel(commentId).then(value => {
                const { code, message } = value
                if (code === 200) {

                    this.reload();
                }else {
                    alert(message);
                }
            })
        },

    },
}
</script>

<style>
/*    .touXiang{
        margin-right: 7px;
    }*/
    .media-content{
        margin-left: 10px;
    }
</style>
