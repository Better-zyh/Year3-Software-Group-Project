<template>
  <div class ="allbody">
  <div class = "main" >
    <identifying_code></identifying_code>
  </div>
  </div>
</template>

<script>
import identifying_code from '../components/identifying_code.vue'
import {updateUsername} from "../api/post";
import { getInfoByName} from '../api/post'
import { getimgs} from '../api/post'
import PostList from '../views/Post'
import SideBar from '../views/sideBar/sideCol'
import MainHeader from '../components/MainHeader.vue'
import HistoryAndLiked from '../views/HistoryAndLiked'
export default {
  name: "identifying",
  components: {identifying_code},
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Input the password again'))
      } else if (value !== this.passForm.new_password) {
        callback(new Error('Inconsistent password!'))
      } else {
        callback()
      }
    };
    return {
      hideUpload: false,
      dialogImageUrl: '',
      dialogVisible: false,//图片预览弹窗
      formLabelWidth: '80px',
      limitNum: 1,
      form: {},
      headimgurl:'',
      active: false,
      numberValidateForm: {
        age: ''
      },
      headForm: {
        alias: 'null',
        old_password: 'null',
        new_password: 'null',
        avatar: ''
      },
      codeVisible: true,
      headVisible: false,
      passVisible: false,
      nameVisible: false,
      DialogVisible: false,
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",

      ruleForm: {
        active: '',
        alias: "",
        old_password: 'null',
        new_password: 'null',
        avatar: 'null'

      },
      passForm: {
        alias: 'null',
        avatar: 'null',
        checkPass: '',
        old_password: "",
        new_password: ""
      },
      rules: {
        alias: [
          {required: true, message: 'Input username', trigger: 'blur'}, //必填项验证
          {min: 3, max: 10, message: '3 to 10 words are allowed', trigger: 'blur'}//长度验证
        ],
      },
      ruleOfPass: {
        old_password: [
          {required: true, message: 'Input old password', trigger: 'blur'}, //必填项验证
          {min: 3, max: 10, message: '3 to 10 words are allowed', trigger: 'blur'}//长度验证
        ],
        new_password: [
          {required: true, message: 'Input new password', trigger: 'blur'}, //必填项验证
          {min: 3, max: 10, message: '3 to 10 words are allowed', trigger: 'blur'}//长度验证
        ],
        checkPass: [
          {required: true, message: 'Input new password again', trigger: 'blur'},
          {validator: validatePass, trigger: 'blur'} //两次密码相同验证方法在data const中定义函数
        ],
      },
      src: '',
      topicUser: {},
      page: {
        current: 1,
        size: 5,
        total: 0
      },
      url:'',
      username: '',
      activeIndex: '1',
      activeIndex2: '1',
      email: '',
      bio: '',
      create_time: '',
      score: '',
      type:false,
    }
  },
  methods: {
    async fetchTopic() {
      await getInfoByName().then(response => {
            const {data} = response
            this.topicUser = data
            this.topicUserName = data.alias
            this.active = data.active;
            this.username = data.alias
            this.email = data.email;
            this.bio = data.bio;
            this.create_time = data.create_time
            this.score = data.score
            this.src = data.avatar
            this.getheadimg(this.username)
          },

      )
    },
  }
}
</script>

<style scoped>
.allbody{
  height: 300%;
  width: 100%;
  right: 0px;
  left: 0px;
  background: #f4f5f5;
}
.MainHeader {
  top:0;
  height: 10%;
  left: 0;
  right: 0;
  -webkit-text-fill-color: black;
}
.main{
  margin-top:-5%;
  top:20%;
  height:70%;
  position: relative;
  left:40%;
  width:30%;

}

</style>