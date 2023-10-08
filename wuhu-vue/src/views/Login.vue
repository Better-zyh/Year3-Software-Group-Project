<template>
  <div class="login-wrap">

    <div class="wuhu-title" style="font-family: cursive;  ">WUHU~XJTLU</div>

    <div class="wuhu-login">
      <el-form ref="ruleForm" :model="ruleForm" :rules="rules" >
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" placeholder="Input the username"></el-input>
        </el-form-item>
        <!--                密码，input标签绑定loginForm对象中的密码-->
        <el-form-item prop="password">
          <el-input v-model="ruleForm.password" type="password" placeholder="Input the password"></el-input>
        </el-form-item>
        <!--                按钮组件，都放在el-form-item标签里面，加一个选择器好改样式-->
        <el-form-item class="login-btn">
          <el-button type="primary" round v-on:click="submitForm">Log in</el-button>

          <el-button type="warning" round v-on:click="centerDialogVisible=true">Register</el-button>

        </el-form-item>

        <el-form-item class="redo">
          <el-button icon="el-icon-refresh-left" type="info" circle v-on:click="resetLoginForm"></el-button>
        </el-form-item>

        <el-form-item class="guest">
          <el-button icon="el-icon-s-custom" type="text" v-on:click="visitorLogin">Enter as a Guest</el-button>
        </el-form-item>
      </el-form>
    </div>


    <div >
      <el-dialog title="Registration" :visible.sync="centerDialogVisible" width="600px" center>
        <div>
          <el-form
                  ref="regruleForm"
                  v-loading="loading"
                  :model="regruleForm"
                  status-icon
                  :rules="rules"
                  label-width="100px"
                  class="demo-ruleForm"
          >
            <el-form-item label="Username" prop="username">
              <el-input v-model="regruleForm.username" />
            </el-form-item>

            <el-form-item label="Password" prop="password">
              <el-input
                      v-model="regruleForm.password"
                      type="password"
                      autocomplete="off"
              />
            </el-form-item>

            <el-form-item label="Retype" prop="checkPass">
              <el-input
                      v-model="regruleForm.checkPass"
                      type="password"
                      autocomplete="off"
              />
            </el-form-item>

            <el-form-item label="e-mail" prop="email">
              <el-input v-model="regruleForm.email" autocomplete="off" />
            </el-form-item>

            <el-form-item>
              <el-button
                      type="primary"
                      @click="registerSubmitForm('regruleForm')"
                      class="login-btn"
              >Submit</el-button>
              <el-button @click="resetForm('regruleForm')" class="login-btn">Reset</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
  </div>
  </div>
</template>

<script>
  import {setToken} from "../utils/auth"
  export default {
    data(){
      const validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Input the password again'))
        } else if (value !== this.regruleForm.password) {
          callback(new Error('Inconsistent input!'))
        } else {
          callback()
        }
      };
      // 邮箱验证:学生邮箱正则
      const checkEmail = (rule, value, callback) => {
  /*      String regex6 = “[1-9]\d{7,10}@qq\.com”;
        String s7 = “514668465@qq.com”;*/
        const mailReg = /^([a-zA-Z0-9_])+[- | a-z0-9A-Z . _]+@student.xjtlu.edu.cn+/;
        if (!value) {
          return callback(new Error("E-mail cant be empty"));
        }
        setTimeout(() => {
          if (mailReg.test(value)) {
            callback();
          } else {
            callback(new Error("Please input with the correct form"));
          }
        }, 100);
      };
      return{
        centerDialogVisible: false,
        loading: false,
        //model对象
        ruleForm:{
          username:"admin",
          password:"12345",
        },
        regruleForm:{
          username:"",
          password:"",
          checkPass: '',
          email: ''
        },

        //rules对象
        rules:{
          username: [
            { required: true, message: 'Input the username', trigger: 'blur' }, //必填项验证
            { min: 3, max: 10, message: '3 to 10 words are allowed', trigger: 'blur' }//长度验证
          ],
          password:[
            { required: true, message: 'Input the password', trigger: 'blur' }, //必填项验证
            { min: 3, max: 10, message: '3 to 10 words are allowed', trigger: 'blur' }//长度验证
          ],
          checkPass: [
            { required: true, message: 'Input the password again', trigger: 'blur' },
            { validator: validatePass, trigger: 'blur' } //两次密码相同验证方法在data const中定义函数
          ],
          email: [
            { required: true, message: 'Input the E-mail', trigger: 'blur' },
            {
              type: 'email',
              message: 'Input the correct e-mail!',
              trigger: ['blur', 'change'],
              validator: checkEmail, //正则验证邮箱格式
            }
          ],
        }
      };
    },

    methods:{
      //绑定上面重置按钮的重置表单方法
      resetLoginForm(){
        this.$refs.ruleForm.resetFields();
      },
      //登录表单提交
      submitForm(){

        let params = new URLSearchParams();
        params.append("name",this.ruleForm.username);
        params.append("password",this.ruleForm.password);
        this.$refs.ruleForm.validate(async valid =>{
          if(!valid) return;
          const {data:res} = await this.$http.post("user/login", params);
          if(res.code == 1){
            this.$message.success("Success to login");
            //后端存session
            //前端信息存入缓存。用localStorage,这是html5的一个特性，把数据像map一样放在里面。
            //localStorage.setItem("name",res.name);
            localStorage.setItem("username",res.username);
            localStorage.setItem("token",res.token);
            setToken(res.token);
           /* this.$cookies.set("cookie",res.token)*/
            await this.$router.push({path: "/home"});
          }else {
            localStorage.setItem("username",null);
            localStorage.setItem("token",null);
            this.$message.error("Fail to log in");
          }
        })
      },
      //注册表单重置 和登录表单重置是一样的
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
      //游客登录
      visitorLogin(){
        localStorage.setItem("username",null);
        localStorage.setItem("token",null);
        this.$router.push({path: "/home"});
      },

      registerSubmitForm(formName) {

        this.$refs[formName].validate(async valid => {
          if (valid) {
            this.loading = true;
            const {data: res} = await this.$http.post("user/register", this.regruleForm);
            //res里取code, message
            //const { code, message } = value
            if (res.code === 200) {
              this.$message({
                message: 'Success to register',
                type: 'success'
              });
              setTimeout(() => {
                this.loading = false;
                this.$router.push({path: "/"})
              }, 0.1 * 1000)
            } else {
              this.$message.error('Fail to register，' + res.message);
            }
          }else {
            return false
          }
        })
      },
      // 注册 userDTO是从函数中传进来的参数,在提交注册表单函数中调用
    }
  }

</script>

<style scoped>
  .login-wrap {
    margin: 0;
    padding: 0;
    background-image: linear-gradient(125deg,#2c3e50,#27ae60,#2980b9,#e74c3c,#8e44ad);
    background-size: 500%;
    animation: bganimation 15s infinite;
    position: relative;
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
  @keyframes bganimation {
    0%{
      background-position: 0% 50%;
    }
    50%{
      background-position: 100% 50%;
    }
    100%{
      background-position: 0% 50%;
    }
  }
  .wuhu-title {
    position: absolute;
    top: 50%;
    width: 100%;
    margin-top: -230px;
    text-align: center;
    font-size: 55px;
    font-weight: 600;
    color: #fff;
  }
  .wuhu-login{
    /*这里的样式适用在wuhu-login的标签下面的所有标签*/
    position: absolute;
    /*  离左边和上边都是50% 标签内容整个宽度300，在正中间，再左移150*/
    left: 50%;
    top: 50%;
    width: 500px;
    height: 160px;
    margin-left: -250px;
    margin-top: -150px;
    padding: 40px;
    /*border: 10px solid white;*/
    border-radius: 5px;
    /*background: white;*/
  }
  .login-btn{
    text-align: center;
  }
  .redo{

    position: absolute;
    margin-top: -185px;
    margin-left: 430px;
    -webkit-text-fill-color: black;

  }
  .guest{

    position: absolute;
    margin-top:10px;
    margin-left: 170px;
    -webkit-text-fill-color: white;

  }
</style>
