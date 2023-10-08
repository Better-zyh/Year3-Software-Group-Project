<template>
  <div class ="allbody">
     <div class = "personinfor" v-show = "active === true">
      <div class = "MainHeader">
        <main-header></main-header>
      </div>
     <div class ="body">
      <div class ="title">
        <div class = "idandimg">
          <el-row>
            <el-col :span="12">
              <div v-if="src === 'null'">
                <img src="../assets/img/head.jpg" id ="headimg" width = 180px v-on:click="headVisible=true">
              </div>
              <div v-if="src != 'null'">
                <img :src="headimgurl" id ="headimg2" width = 180px v-on:click="headVisible=true">
              </div>
            </el-col>
          </el-row>
          <div class="name" v-on:click="nameVisible=true">
            {{username}}
          </div>

        </div>
        <div class = "button">
          <el-button icon="el-icon-setting" type="text" v-on:click="DialogVisible=true" >Edit</el-button>
        </div>
        <div >
          <el-dialog title="Edit self-information" :visible.sync="DialogVisible" width="600px"  center>
            <div>
              <el-avatar class="change-head" shape="circle" :size="150" :src="circleUrl">
              </el-avatar></div>
            <el-button v-on:click="headVisible=true" >Edit head</el-button>
            <el-button v-on:click="nameVisible=true" >Edit name</el-button>
            <el-button v-on:click="passVisible=true" >Edit password</el-button>

          </el-dialog>
        </div>

        <div >
          <el-dialog title="Edit head" :visible.sync="headVisible" width="200px"  center>
            <el-form :model="form">
              <el-upload
                  class="avatar-uploader"
                  action="#"
                  multiple
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload">
                  <img v-if="imageUrl" :src="imageUrl" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>

            </el-form>
          </el-dialog>
        </div>


        <div >
          <el-dialog title="Edit information" :visible.sync="nameVisible" width="500px"  center>
            <div>
              <el-form
                  ref="ruleForm"
                  v-loading="loading"
                  :model="ruleForm"
                  status-icon
                  :rules="rules"
                  label-width="100px"
                  class="demo-ruleForm"
              >
                <el-form-item label="username" prop="alias">
                  <el-input v-model="ruleForm.alias" />
                </el-form-item>


                <el-button
                    type="primary"
                    @click="changeData()"
                >Finish</el-button>
              </el-form>
            </div>
          </el-dialog>
        </div>

        <div >
          <el-dialog title="Edit password" :visible.sync="passVisible" width="700px"  center>
            <div>
              <el-form
                  ref="passForm"
                  v-loading="loading"
                  :model="passForm"
                  status-icon
                  :rules="ruleOfPass"
                  label-width="180px"
                  class="demo-ruleForm"
              >
                <el-form-item label="Old password" prop="old_password">
                  <el-input v-model="passForm.old_password" />
                </el-form-item>
                <el-form-item label="New password" prop="new_password">
                  <el-input v-model="passForm.new_password" />
                </el-form-item>
                <el-form-item label="Confirm password" prop="checkPass">
                  <el-input v-model="passForm.checkPass" />
                </el-form-item>
                <el-button
                    type="primary"
                    @click="changePass('passForm')"
                >Finish</el-button>
              </el-form>
            </div>
          </el-dialog>
        </div>
      </div>
      <div class = "main" v-if="this.type=== true">
        <el-card class="box-card" center>
          <div class ="el-icon-time" style='font-size: 14px' >
            {{"Entrant-time："+dayjs(create_time).format('YYYY/MM/DD HH:mm:ss')}}
          </div>
        </el-card>
          <el-card class="box-card" center>
            <div class ="text item">
              <div class ="el-icon-user">
              {{"Occupation     ："+bio}}
              </div>
            </div>
            <div class ="text item">
              <div class ="el-icon-s-promotion">
              {{"E-mail     ："+email}}
              </div>
            </div>
            <div class ="text item">
              <div class ="el-icon-trophy">
              {{"Credit    ："+ score}}
              </div>
            </div>
          </el-card>
           <div class = "box-card">
             <SideBar></SideBar>
           </div>
        <div class="post">
          <HistoryAndLiked></HistoryAndLiked>
        </div>
      </div>
     </div>
  </div>
  </div>
</template>

<script>
import {updateUsername} from "../api/post";
import { getInfoByName} from '../api/post'
import { getimgs} from '../api/post'
import PostList from '../views/Post'
import SideBar from '../views/sideBar/sideCol'
import MainHeader from '../components/MainHeader.vue'
import identifying_code from '../components/identifying_code.vue'
import HistoryAndLiked from '../views/HistoryAndLiked'
export default {
  name: 'Profile',
  components: {HistoryAndLiked: HistoryAndLiked,SideBar, TopicList: PostList, MainHeader,identifying_code},
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
    beforeAvatarUpload(file) { //上传前的函数
      //上传前对图片类型和大小进行判断
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      //校验成功上传文件
      if(isJPG && isLt2M == true){
        console.log(file);

        //将文件转化为formdata数据上传
        let fd = new FormData()
        fd.append('file', file)
        console.log(fd)

        // post上传图片

        let that = this
        let token = localStorage.getItem('token');
        new Promise(function (resolve, reject) {
          that.axios.post('/user/upload', fd,
              {
                headers: {
                  'content-type': 'application/json',
                  "Authorization":'Bearer ' + token
                }
              }).then((response) => {
            that.imgId = response.data.data
            resolve(that.imgId);
          }).catch((error) =>{
            this.$message.error('头像上传失败，请重新上传!');
          })
        })
      }
      return isJPG && isLt2M;

    },
    getheadimg(username){
     this.axios.get('/user/getimgs',{params:{ userName: username},responseType: "arraybuffer",withCredentials: true})
      .then(res =>{
        this.headimgurl = "data:image/jpg;base64,"+ btoa(
                    new Uint8Array(res.data).reduce(
                        (data,byte) => data + String.fromCharCode(byte),
                        ""
                    )
                )

      })
    },

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
        if(this.active === true){
          this.type = true;
        }
        if(this.active === false){
          this.type = false;
        }
           this.getheadimg(this.username)
      },

      )
    },
    changeData() {
      this.$refs.ruleForm.validate(async valid => {
        if (valid) {
          updateUsername(this.ruleForm).then((response) => {
            const {data} = response;
            alert("success!!");
            this.nameVisible = false;
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    changePass() {
      this.$refs.passForm.validate(async valid => {
        if (valid) {
          updateUsername(this.passForm).then((response) => {
            const {data} = response;
            alert("success!!");
            this.passVisible = false;
            this.$router.push({path: "/"});
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },


  },
  created() {
    this.fetchTopic();
    //页面加载时就从本地通过localstorage获取存储的token值
    this.token = localStorage.getItem('token');
    //this.username = localStorage.getItem('username')
    //alert(this.topicUserName);

  },

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

.personinfor{
  height: 100%;
  width: 100%;
  right: 0px;
  left: 0px;
  position:absolute;

}
.MainHeader {
  top:0;
  height: 10%;
  left: 0;
  right: 0;
  -webkit-text-fill-color: black;
}

.post{
  margin-top:-1%;
  top:3%;
  width:60%;
  left:30%;
  height:90%;
  position:absolute;
}
.main{
  margin-top:-5%;
  top:20%;
  height:70%;
  position: relative;

}

date{
  margin-top:5%;
  height:30%;
  position:absolute;
}
headimg{
  width:180px;
  height:180px;
}
.idandimg{
  top:0%;
  height:40%;
  width:100%;
  display: flex;
  position:absolute;
  justify-content: center;
}
.title{
  top:0%;
  height:40%;
  width:100%;
  position:absolute;
}
.button{
  top:12%;
  width:5%;
  right:10%;
  position: absolute;
}
.name{
  margin-top:90px;

  margin-right:4%;
  margin-bottom:5%;
  display: flex;
  position: absolute;
}
.Navmenu{
  top:15%;
  width:50%;
  height:10%;
  margin-top:2%;
  margin-left:35%;
  position: absolute;
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
  margin-top:3%;
  margin-left:5%;
  left:15%;
  width: 20%;
}
.body{
  top:10%;
  left:0;
  right:0;
  bottom:0;
  position:absolute;
}
.edit-table{
  margin-left: 33%;
  margin-top: 10px;
  -webkit-text-fill-color: white;
}
.change-head{
  text-align: center;
}
.hide .el-upload--picture-card {
  display: none;
}
</style>
